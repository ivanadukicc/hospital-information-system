package com.ivana.demo.controllers;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivana.demo.dto.LekarDTO;
import com.ivana.demo.dto.SpecMesecStatDTO;
import com.ivana.demo.repositories.PacijentRepo;
import com.ivana.demo.services.AdminService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import model.Termin;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService as;
	@Autowired
	PacijentRepo pr;

	@GetMapping
	public String tabela(Model m) {
		LocalDate danas = LocalDate.now();
		long danasBroj = as.brojTerminaZaDan(danas);
		var top7 = as.rangLekara(LocalDate.now().minusDays(7), LocalDate.now());

		m.addAttribute("danasBroj", danasBroj);
		m.addAttribute("top7", top7);
		return "admin-index";
	}

	@GetMapping("/lekari/nov")
	public String noviLekarForm(Model m) {
		if (!m.containsAttribute("lekarDTO")) {
			m.addAttribute("lekarDTO", new LekarDTO());
		}
		return "admin-lekar-novi";
	}

	@PostMapping("/lekari")
	public String sacuvajLekara(@Valid @ModelAttribute("lekarDTO") LekarDTO dto, Model m) {
		try {
			as.kreirajNovogLekara(dto);
			m.addAttribute("ok", "Lekar je uspešno dodat.");
			m.addAttribute("lekarDTO", new LekarDTO());
		} catch (Exception e) {
			m.addAttribute("err", e.getMessage());
			m.addAttribute("lekarDTO", dto);
		}
		return "admin-lekar-novi";
	}

	private static LocalDateTime parseDT(String s) {
		try {
			if (s == null || s.isBlank())
				return null;
			return LocalDateTime.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping("/termini")
	public String termini(@RequestParam(required = false) Long lekarId, @RequestParam(required = false) String od,
			@RequestParam(required = false) String doo, @RequestParam(defaultValue = "0") int page, Model m) {
		LocalDateTime from = parseDT(od);
		LocalDateTime to = parseDT(doo);

		Page<Termin> p = as.pretragaTermina(lekarId, from, to, page, 20);
		m.addAttribute("page", p);
		m.addAttribute("lekarId", lekarId);
		m.addAttribute("od", od);
		m.addAttribute("doo", doo);
		return "admin-termini";
	}

	@RequestMapping(value= "/getSpisakPacijenata.pdf", method=RequestMethod.GET)
	 public void sviPacijenti(HttpServletResponse response) throws Exception {
		 JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pr.findAll());
		 InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/SpisakPacijenataReport.jrxml");
		 JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		 JasperPrint jp = JasperFillManager.fillReport(jasperReport,null, dataSource);
		 inputStream.close();
		 
		 response.setContentType("application/x-download");
		 response.addHeader("Content-disposition", "attachment; filename-SpisakPacijenataReport.pdf");
		 OutputStream out = response.getOutputStream();
		 JasperExportManager.exportReportToPdfStream(jp, out);
		 
	 }

	@RequestMapping(value= "/getRangLekara.pdf", method=RequestMethod.GET)
	 public void rangLekara(HttpServletResponse response, 
			 @RequestParam(name = "od", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate od,
			 @RequestParam(name = "doo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate doo) throws Exception {
		 
		 List<LekarDTO> data = as.rangLekara(od, doo);
		 
		 JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		 InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/RangLekaraUPeriodu.jrxml");
		 JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		 Map<String, Object> params = new HashMap<>();
		 params.put("od",  od  != null ? od.toString()  : "");
		 params.put("doo", doo != null ? doo.toString() : "");
         
		 JasperPrint jp = JasperFillManager.fillReport(jasperReport,params, dataSource);
		 inputStream.close();
		 
		 response.setContentType("application/pdf");
		 response.addHeader("Content-disposition", "attachment; filename=RangLekaraUPeriodu.pdf");
		 OutputStream out = response.getOutputStream();
		 JasperExportManager.exportReportToPdfStream(jp, out);
		 out.flush();
		 
	 }
	
	@RequestMapping(value= "/getSpecMesecStat.pdf", method=RequestMethod.GET)
	public void specMesecStat(HttpServletResponse response) throws Exception {
	    List<SpecMesecStatDTO> data = as.specStatMesecno();

	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
	    InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/SpecStat.jrxml");
	    JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
	    JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, dataSource);
	    inputStream.close();

	    response.setContentType("application/pdf");
	    response.addHeader("Content-disposition", "attachment; filename=SpecMesecStatReport.pdf");
	    OutputStream out = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jp, out);
	    out.flush();
	}
}
