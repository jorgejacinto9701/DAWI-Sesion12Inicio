package com.empresa.servicio;

import java.util.List;

import com.empresa.entidad.Disponibilidad;

public interface DisponibilidadService {

	public List<Disponibilidad> listaPorCicloHoraInicioAndFin(int idCiclo, String horaInicio, String horaFin);
	
	
}
