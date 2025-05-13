package com.tareas.organizador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tareas.organizador.dtos.TareaDTO;
import com.tareas.organizador.entitie.Importancia;
import com.tareas.organizador.service.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    // Agregar una nueva tarea
    @PostMapping
    public ResponseEntity<TareaDTO> agregarTarea(@RequestBody TareaDTO dto) {
        TareaDTO nueva = tareaService.agregarTarea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // Eliminar una tarea por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todas las tareas
    @GetMapping
    public ResponseEntity<List<TareaDTO>> listarTareas() {
        return ResponseEntity.ok(tareaService.listarTodas());
    }

    // Listar tareas por importancia
    @GetMapping("/importancia/{nivel}")
    public ResponseEntity<List<TareaDTO>> listarPorImportancia(@PathVariable Importancia nivel) {
        return ResponseEntity.ok(tareaService.listarPorImportancia(nivel));
    }

    // Listar tareas por fecha de entrega (orden ascendente)
    @GetMapping("/fecha")
    public ResponseEntity<List<TareaDTO>> listarPorFechaEntrega() {
        return ResponseEntity.ok(tareaService.listarPorFechaEntrega());
    }

    // Endpoint para probar envío de correo manualmente (opcional)
    @PostMapping("/probar-correo")
    public ResponseEntity<String> probarCorreo() {
        tareaService.verificarVencimientos();
        return ResponseEntity.ok("Verificación ejecutada y correos enviados si era necesario.");
    }
}
