package com.tareas.organizador.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tareas.organizador.dtos.TareaDTO;
import com.tareas.organizador.entitie.Importancia;
import com.tareas.organizador.entitie.Tarea;
import com.tareas.organizador.repository.TareaRepository;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    private static final String DESTINATARIO = "nicolas.achaparro@gmail.com";

    public TareaService(TareaRepository tareaRepository, ModelMapper modelMapper, EmailService emailService) {
        this.tareaRepository = tareaRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    public TareaDTO agregarTarea(TareaDTO dto) {
        Tarea tarea = modelMapper.map(dto, Tarea.class);
        Tarea guardada = tareaRepository.save(tarea);

        emailService.enviarCorreo(
            DESTINATARIO,
            "Nueva tarea registrada",
            "Se ha registrado una nueva tarea:\n\n" +
            "Materia: " + tarea.getNombreMateria() + "\n" +
            "Descripción: " + tarea.getDescripcion() + "\n" +
            "Importancia: " + tarea.getImportancia() + "\n" +
            "Fecha de entrega: " + tarea.getFechaEntrega()
        );

        return modelMapper.map(guardada, TareaDTO.class);
    }

     public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    public List<TareaDTO> listarTodas() {
        return tareaRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TareaDTO.class))
                .collect(Collectors.toList());
    }

    public List<TareaDTO> listarPorImportancia(Importancia importancia) {
        return tareaRepository.findAllByImportancia(importancia).stream()
                .map(t -> modelMapper.map(t, TareaDTO.class))
                .collect(Collectors.toList());
    }

    public List<TareaDTO> listarPorFechaEntrega() {
        return tareaRepository.findAllByOrderByFechaEntregaAsc().stream()
                .map(t -> modelMapper.map(t, TareaDTO.class))
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 9 * * ?") // Todos los días a las 9 AM
    public void verificarVencimientos() {
        List<Tarea> proximas = tareaRepository.findByFechaEntregaBefore(LocalDate.now().plusDays(1));
        if (!proximas.isEmpty()) {
            String cuerpo = proximas.stream()
                    .map(t -> "- " + t.getNombreMateria() + " (vence " + t.getFechaEntrega() + ")")
                    .collect(Collectors.joining("\n"));

            emailService.enviarCorreo(
                DESTINATARIO,
                "⚠️ Tareas próximas a vencerse",
                "Estas tareas están próximas a vencerse:\n\n" + cuerpo
            );
        }
    }


}
