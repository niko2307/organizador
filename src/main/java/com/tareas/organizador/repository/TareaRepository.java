package com.tareas.organizador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tareas.organizador.entitie.Importancia;
import com.tareas.organizador.entitie.Tarea;

@Repository
public interface  TareaRepository  extends JpaRepository<Tarea, Long>{
    List<Tarea> findAllByImportancia(Importancia importancia);
    List<Tarea> findAllByOrderByFechaEntregaAsc();
    List<Tarea> findByFechaEntregaBefore(LocalDate fecha);
    
}
