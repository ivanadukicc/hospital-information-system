package com.ivana.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.ivana.demo.services.LekService;
import com.ivana.demo.services.PregledService;
import com.ivana.demo.services.ReceptService;
import com.ivana.demo.services.TerminService;

import model.Pregled;

@Controller
@RequestMapping("/lekar/recept")
public class ReceptController {

	@Autowired
	ReceptService rs;
	@Autowired
	LekService ls;
	@Autowired
	PregledService ps;
	@Autowired
	TerminService ts;

	@GetMapping("/nov")
	public String noviRecept(@RequestParam(required = false) Long pregledId,
			@RequestParam(required = false) Long terminId, Model model, RedirectAttributes ra) {

		if (pregledId == null && terminId == null) {
			ra.addFlashAttribute("err", "Moraš proslediti pregledId ili terminId.");
			return "redirect:/lekar";
		}

		if (pregledId != null) {
			var pregled = ps.findById(pregledId).orElseThrow(() -> new IllegalArgumentException("Pregled ne postoji"));
			model.addAttribute("pregled", pregled);
		} else {
			var termin = ts.findById(terminId).orElseThrow(() -> new IllegalArgumentException("Termin ne postoji"));

			if (termin.getPregled() == null) {
				return "redirect:/lekar/pregled/nov?terminId=" + terminId + "&next=recept";
			}
			model.addAttribute("pregled", termin.getPregled());
		}

		model.addAttribute("lekovi", ls.findAll());
		return "recept-novi";
	}

	@PostMapping("/izdaj")
	public String izdaj(@RequestParam(required = false) Long pregledId, @RequestParam(required = false) Long terminId,
			@RequestParam Long lekId, @RequestParam String doza, @RequestParam(required = false) Integer kolicina,
			@RequestParam(required = false) String trajanje, @RequestParam(required = false) String ucestalost,
			@RequestParam(required = false) String napomena, RedirectAttributes ra) {

		if (pregledId == null && terminId == null) {
			ra.addFlashAttribute("err", "Nedostaje pregledId ili terminId.");
			return "redirect:/lekar";
		}

		Pregled pregled;

		if (pregledId != null) {
			pregled = ps.findById(pregledId).orElseThrow(() -> new IllegalArgumentException("Pregled ne postoji"));
		} else {
			var termin = ts.findById(terminId).orElseThrow(() -> new IllegalArgumentException("Termin ne postoji"));

			if (termin.getPregled() == null) {
				return "redirect:/lekar/pregled/nov?terminId=" + terminId + "&next=recept";
			}
			pregled = termin.getPregled();
		}

		rs.izdaj(pregled.getId(), napomena, lekId, doza, kolicina, trajanje, ucestalost);
		ra.addFlashAttribute("ok", "Recept je uspešno izdat.");
		return "redirect:/lekar/termini?lekarId=" + pregled.getTermin().getLekar().getId();
	}

}
