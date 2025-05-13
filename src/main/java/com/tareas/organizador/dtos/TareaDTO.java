package com.tareas.organizador.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.tareas.organizador.entitie.Importancia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TareaDTO {
    private Long id;
    private String nombreMateria;
    private String descripcion;
    private LocalTime hora;
    private LocalDate fechaEntrega;
    private Importancia importancia;
}
