package com.empresa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entidad.Disponibilidad;
import com.empresa.entidad.ReporteBean;

public interface DisponibilidaRepository extends JpaRepository<Disponibilidad, Integer>{


	@Query("select d from Disponibilidad d where (?1 is -1 or d.ciclo.idCiclo = ?1) and (?2 is '' or d.horaInicio = ?2) and (?3 is '' or d.horaFin = ?3)" )
	public List<Disponibilidad> listaPorCicloHoraInicioAndFin(int idCiclo, String horaInicio, String horaFin);
	
	
	@Query(value="SELECT new com.empresa.entidad.ReporteBean(d.dia, count(d))  from Disponibilidad d group by d.dia")
	public List<ReporteBean> listaReportePorDia();
	
	@Query(value="SELECT new com.empresa.entidad.ReporteBean(d.dia, d.ciclo.nombre, count(d))  from Disponibilidad d group by d.dia, d.ciclo.nombre")
	public List<ReporteBean> listaReportePorDiaCiclo();
	
}
