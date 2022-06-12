package com.empresa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.entidad.Disponibilidad;
import com.empresa.repositorio.DisponibilidaRepository;

@Service
public class DisponibilidadServiceImpl implements DisponibilidadService {

	@Autowired
	private DisponibilidaRepository repository;
	
	@Override
	public List<Disponibilidad> listaPorCicloHoraInicioAndFin(int idCiclo, String horaInicio, String horaFin) {
		return repository.listaPorCicloHoraInicioAndFin(idCiclo, horaInicio, horaFin);
	}

	
	

}
