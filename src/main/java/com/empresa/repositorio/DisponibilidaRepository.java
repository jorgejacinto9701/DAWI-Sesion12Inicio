package com.empresa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empresa.entidad.Disponibilidad;

public interface DisponibilidaRepository extends JpaRepository<Disponibilidad, Integer>{


	@Query("select d from Disponibilidad d where "
					+ "(:param_ciclo is -1 or d.ciclo.idCiclo = :param_ciclo) and "
					+ "(:param_inicio is '' or d.horaInicio = :param_inicio) and "
					+ "(:param_fin is '' or d.horaFin = :param_fin)" )
	public List<Disponibilidad> listaPorCicloHoraInicioAndFin
										(@Param("param_ciclo") int idCiclo , 
										 @Param("param_inicio") String horaInicio, 
										 @Param("param_fin") String horaFin);
	
}
