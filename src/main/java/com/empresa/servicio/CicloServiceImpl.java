package com.empresa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.entidad.Ciclo;
import com.empresa.repositorio.CicloRepository;

@Service
public class CicloServiceImpl implements CicloService{

	@Autowired
	private  CicloRepository repository;
	
	@Override
	public List<Ciclo> listaCiclo() {
		return repository.findAll();
	}

}
