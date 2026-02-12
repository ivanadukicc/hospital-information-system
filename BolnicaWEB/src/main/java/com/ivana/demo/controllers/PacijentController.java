package com.ivana.demo.controllers;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ivana.demo.services.IzabraniLekarService;
import com.ivana.demo.services.KorisnikService;
import com.ivana.demo.services.LekarService;
import com.ivana.demo.services.PacijentService;
import com.ivana.demo.services.ReceptService;
import com.ivana.demo.services.TerminService;
import model.Pacijent;
import model.Recept;



@Controller
@RequestMapping(value = "/pacijent")
public class PacijentController {
	
	@Autowired
	private LekarService ls;
	@Autowired
	private TerminService ts;
	@Autowired
	KorisnikService ks;
	@Autowired
	ReceptService rs;
	@Autowired
	PacijentService ps;
	@Autowired
	IzabraniLekarService ils;

	@GetMapping({"","/"})
	public String pacijentHome(Principal p,Model model) {
	    Pacijent pacijent = ps.findByKorisnikUsername(p.getName()).orElseThrow(() -> new IllegalStateException("Pacijent nije pronadjen"));
	    model.addAttribute("pacijentId", pacijent.getId());
	    model.addAttribute("ime", pacijent.getIme());
	    model.addAttribute("prezime", pacijent.getPrezime());
	    var izabrani = ils.zaPacijenta(pacijent.getId());
	    izabrani.ifPresent(lekar -> model.addAttribute("izabraniLekar", lekar));
	    return "index-pacijent";
	}

	@GetMapping("/lekari")
	public String lekari(@RequestParam(required=false) String q, Model model) {
		model.addAttribute("lekari", ls.findBySpecijalnost(q));
		model.addAttribute("q", q);
		return "lekari";
	}
	
	@GetMapping("/lekari/{id}")
	public String detalji(@PathVariable Long id, Model model) {
	    model.addAttribute("lekar", ls.findOne(id).orElseThrow());
	    return "lekar-detalji";
	}
	 
	
	@GetMapping("/termin/nov")
	public String noviTermin(@RequestParam Long lekarId, Model model) {
		model.addAttribute("lekar", ls.findOne(lekarId).orElseThrow());
		return "termin-novi";
	}
	
	private Long currentPacijentId(Principal principal) {
		return ps.findByKorisnikUsername(principal.getName())
				.map(p -> p.getId())
				.orElseThrow(() -> new IllegalStateException("Pacijent nije pronadjen"));
	}
	
	@PostMapping({ "/termin"})
	public String sacuvajTermin(@RequestParam Long lekarId, @RequestParam 
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date pocetak,
			@RequestParam(required = false) Integer trajanjeMin,
			@RequestParam(required = false) String napomena,
			RedirectAttributes ra,
			Principal principal) {
				
		 Long pacijentId = currentPacijentId(principal);
		try {
			ts.zakazi(lekarId, pacijentId, pocetak, napomena, trajanjeMin);
			ra.addFlashAttribute("ok", "Termin uspesno zakazan. Potvrda je poslata na Vas email.");
			return  "redirect:/pacijent/termini/moji";
		} catch (IllegalStateException e) {
			ra.addFlashAttribute("err",e.getMessage());
			return "redirect:/pacijent/termin/nov?lekarId=" + lekarId;
		}
		
		
	}
	
	@GetMapping("/termini/moji")
	public String mojiTermini(@RequestParam(defaultValue = "false") boolean samoAktivni,
	                          Model model, Principal principal) {
		Long pacijentId = currentPacijentId(principal);
		var lista = samoAktivni
	            ? ts.aktivniZaPacijenta(pacijentId)
	            : ts.zaPacijenta(pacijentId);
	    model.addAttribute("termini", lista);
	    model.addAttribute("samoAktivni", samoAktivni);
	    return "termini-moji";
	}


	
	@PostMapping("/termin/{id}/otkazi")
	public String otkaziTermin(@PathVariable("id") Long id,
	                           RedirectAttributes ra) {
	    try {
	        ts.otkazi(id);
	        ra.addFlashAttribute("ok", "Termin je otkazan.");
	    } catch (IllegalStateException | IllegalArgumentException e) {
	        ra.addFlashAttribute("err", e.getMessage());
	    }
	    return "redirect:/pacijent/termini/moji";
	}
	
	@GetMapping("/recepti")
	public String mojiRecepti(Principal p, Model model) {
		Pacijent pacijent = ps.findByKorisnikUsername(p.getName()).orElseThrow(() -> new IllegalStateException());
		List<Recept> lista = rs.zaPacijenta(pacijent.getId());
		model.addAttribute("recepti", lista);
		return "pacijent-recepti";
	}


}