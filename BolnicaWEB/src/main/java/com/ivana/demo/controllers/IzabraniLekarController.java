package com.ivana.demo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ivana.demo.services.IzabraniLekarService;
import com.ivana.demo.services.PacijentService;

import model.Pacijent;

@Controller
@RequestMapping("/pacijent/izabrani-lekar")
public class IzabraniLekarController {
	@Autowired
	IzabraniLekarService ils;
	@Autowired
	PacijentService ps;

	
	private Long currentPacijentId(Principal p) {
		return ps.findByKorisnikUsername(p.getName())
				.map(Pacijent::getId)
				.orElseThrow(() -> new IllegalStateException("Nije pronadjen pacijent za korisnika."));
	}
	
	@GetMapping({"", "/"})
	public String prikaz(Model model, Principal principal) {
		Long pacijentId = currentPacijentId(principal);
		var izabrani = ils.zaPacijenta(pacijentId);
		model.addAttribute("izabrani", izabrani.orElse(null));
		return "izabrani-lekar";
	}
	
	@PostMapping({"", "/"})
	public String postavi(@RequestParam Long lekarId, Principal principal, RedirectAttributes ra) {
		Long pacijentId = currentPacijentId(principal);
		ils.postavi(pacijentId, lekarId);
		ra.addFlashAttribute("ok", "Uspesno ste postavili izabranog lekara");
		
		return "redirect:/pacijent/izabrani-lekar";
	}
	
	@PostMapping("/ukloni")
	public String ukloni(Principal principal, RedirectAttributes ra) {
		Long pacijentId = currentPacijentId(principal);
		ils.ukloni(pacijentId);
		ra.addFlashAttribute("ok", "Izabrani lekar je uklonjen");
		return "redirect:/pacijent/izabrani-lekar";
	}
}
