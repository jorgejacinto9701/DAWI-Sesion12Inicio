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
import com.empresa.entidad.ReporteBean;
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
			
			//PASO 1: Obtener el dataSource que va generar el reporte
			List<Disponibilidad> lstDisponibilidad = disponibilidadService.listaPorCicloHoraInicioAndFin(idCiclo, horaInicio, horaFin);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstDisponibilidad);
			
			//PASO 2: Obtener el archivo que contiene el diseño del reporte
			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reporteDisponibilidadUsuarios.jasper");
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			//PASO 3: Parámetros adicionales(NO hay ninguno)
			Map<String,Object> params = new HashMap<String,Object>();
			
			//PASO 4: Enviamos dataSource, diseño y parámetros para generar el PDF
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			
			//PASO 5: Enviar el PDF generado
			response.setContentType("application/x-pdf");
		    response.addHeader("Content-disposition", "attachment; filename=ReporteDisponibilidad.pdf");

			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value =  "/consultaReportGraficoPorDiaPdf", method = RequestMethod.GET)
	@ResponseBody
	public void reportGraficoPorDia(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			List<ReporteBean> lstReporte = disponibilidadService.listaReportePorDia();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstReporte);
			
			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reporteReportGraficoPorDia.jasper");
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			Map<String,Object> params = new HashMap<String,Object>();
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
	
	
	@RequestMapping(value =  "/consultaReportGraficoPorDiaCicloPdf", method = RequestMethod.GET)
	@ResponseBody
	public void reportGraficoPorDiaCiclo(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			List<ReporteBean> lstReporte = disponibilidadService.listaReportePorDiaCiclo();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstReporte);
			
			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reportGraficoPorDiaCiclo.jasper");
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			Map<String,Object> params = new HashMap<String,Object>();
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
