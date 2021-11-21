package com.empresa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entidad.Ciclo;
import com.empresa.entidad.Disponibilidad;
import com.empresa.servicio.CicloService;
import com.empresa.servicio.DisponibilidadService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


@Controller
public class ConsultaDisponibilidadController {

	@Autowired
	private CicloService cicloService;
	
	@Autowired
	private DisponibilidadService disponibilidadService;

	@RequestMapping("/")
	public String ver() {
		return "intranetConsultaDisponibilidad";
	}
	
	@RequestMapping("/cargaCiclo")
	@ResponseBody
	public List<Ciclo> listaCiclo(){
		return cicloService.listaCiclo();
	}
	
	@RequestMapping("/consultaDisponibilidad")
	@ResponseBody
	public List<Disponibilidad> consulta(int idCiclo, String horaInicio, String horaFin){
		return disponibilidadService.listaPorCicloHoraInicioAndFin(idCiclo, horaInicio, horaFin); 
	}
	
	@RequestMapping(value =  "/consultaDisponibilidadPdf", method = RequestMethod.GET)
	@ResponseBody
	public void report(HttpServletRequest request, HttpServletResponse response,int idCiclo, String horaInicio, String horaFin) {
		try {
			
			List<Disponibilidad> lista =  disponibilidadService.listaPorCicloHoraInicioAndFin(idCiclo, horaInicio, horaFin); 

			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reporteDisponibilidadUsuarios.jasper");
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			Map<String,Object> params = new HashMap<String,Object>();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
			  
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

			response.setContentType("application/x-pdf");
		    response.addHeader("Content-disposition", "attachment; filename=ReporteDisponibilidad.pdf");

			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
