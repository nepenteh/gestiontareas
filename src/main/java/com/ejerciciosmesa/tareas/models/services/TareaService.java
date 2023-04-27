package com.ejerciciosmesa.tareas.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.tareas.models.entities.Tarea;

public interface TareaService {
	
	public List<Tarea> findAll();
	public Page<Tarea> findAll(Pageable pageable);
	public Tarea findOne(Long id);
	public void save(Tarea tarea);
	public void remove(Long id);
	public Long count();
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

