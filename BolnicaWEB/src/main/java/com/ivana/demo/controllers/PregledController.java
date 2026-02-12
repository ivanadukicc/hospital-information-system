package com.ivana.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ivana.demo.services.LekService;
import com.ivana.demo.services.PregledService;
import com.ivana.demo.services.ReceptService;
import com.ivana.demo.services.TerminService;

import model.Pregled;
import model.Recept;

@Controller
@RequestMapping("/lekar/pregled")
public class PregledController {


	@Autowired
	PregledService ps;
	@Autowired
	LekService ls;
	@Autowired
	ReceptService rs;
	@Autowired
	TerminService ts;
	
	@GetMapping("/{id}")
	public String detalji(@PathVariable Long id, Model model) {
		Pregled pregled = ps.findById(id).orElseThrow();
		Recept recept = rs.findByPregled_Id(id);
		model.addAttribute("pregled", pregled);
		var lekovi = ls.findAll(); 

		model.addAttribute("pregled", pregled);
		model.addAttribute("recept", recept);
		model.addAttribute("lekovi", lekovi);
		return "pregled-detalji";

	}
	
	
    @PostMapping("/{id}/recept")
    public String izdajRecept(
            @PathVariable Long id,
            @RequestParam Long lekId,
            @RequestParam String doza,
            @RequestParam(required = false) Integer kolicina,
            @RequestParam String trajanje,
            @RequestParam String ucestalost,
            @RequestParam(required = false) String napomena,
            RedirectAttributes ra
    ) {
        rs.izdaj(
                id,
                napomena,
                lekId,
                doza,
                kolicina,
                trajanje,
                ucestalost
        );

        ra.addFlashAttribute("ok", "Recept je uspešno izdat.");
        return "redirect:/lekar/pregled/" + id;
    }
    
  
    
    @GetMapping("/nov")
    public String noviPregled(@RequestParam Long terminId,
                        @RequestParam(required = false) String next,
                        Model model) {
        var termin = ts.findById(terminId)
                .orElseThrow(() -> new IllegalArgumentException("Termin ne postoji"));

        if (termin.getPregled() != null) {
            if ("recept".equals(next)) {
                return "redirect:/lekar/recept/nov?pregledId=" + termin.getPregled().getId();
            }
            return "redirect:/lekar/termini?lekarId=" + termin.getLekar().getId();
        }

        model.addAttribute("termin", termin);
        model.addAttribute("next", next); 
        return "pregled-novi";
    }
    
    @PostMapping("/sacuvaj")
    public String sacuvaj(@RequestParam Long terminId,
                          @RequestParam String dijagnoza,
                          @RequestParam(required = false) String preporuke,
                          @RequestParam(required = false) String next,
                          RedirectAttributes ra) {

        var pregled = ps.kreirajPregled(terminId, dijagnoza, preporuke);
        ra.addFlashAttribute("ok", "Pregled je sačuvan.");

        if ("recept".equals(next)) {
            return "redirect:/lekar/recept/nov?pregledId=" + pregled.getId();
        }
        return "redirect:/lekar/termini?lekarId=" + pregled.getTermin().getLekar().getId();
    }

    
}
	
	

