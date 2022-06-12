package com.empresa.servicio;

import java.util.List;

import com.empresa.entidad.Disponibilidad;
import com.empresa.entidad.ReporteBean;

public interface DisponibilidadService {

	public List<Disponibilidad> listaPorCicloHoraInicioAndFin(int idCiclo, String horaInicio, String horaFin);
	
	public List<ReporteBean> listaReportePorDia();
	
	public List<ReporteBean> listaReportePorDiaCiclo();
}
