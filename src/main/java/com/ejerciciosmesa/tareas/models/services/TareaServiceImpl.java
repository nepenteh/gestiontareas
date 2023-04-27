package com.ejerciciosmesa.tareas.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.tareas.models.dao.TareaDAO;
import com.ejerciciosmesa.tareas.models.entities.Tarea;


@Service
public class TareaServiceImpl implements TareaService {

	private final TareaDAO tareaDAO;
	
	
	
	public TareaServiceImpl(
			
			TareaDAO tareaDAO
			) {
		
		this.tareaDAO = tareaDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Tarea> findAll() {
		return (List<Tarea>) tareaDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Tarea> findAll(Pageable pageable) {
		return tareaDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Tarea findOne(Long id) {
		return tareaDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Tarea tarea) {
		tareaDAO.save(tarea);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		tareaDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return tareaDAO.count();
	}
	
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

