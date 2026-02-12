package com.ivana.demo.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivana.demo.services.KorisnikService;
import com.ivana.demo.services.LekarService;
import com.ivana.demo.services.PregledService;
import com.ivana.demo.services.TerminService;
import model.Lekar;
import model.Termin;


@Controller
@RequestMapping("/lekar")
public class LekarController {

	@Autowired
	LekarService ls;
	
	@Autowired
	TerminService ts;
	
	@Autowired
	KorisnikService ks;
	@Autowired
	PregledService ps;
	
	@GetMapping({"/", ""})
	public String lekarHome(Principal p, Model model) {
	    Lekar lekar = ls.findByKorisnik_Username(p.getName()).orElseThrow(() -> new IllegalStateException("Lekar nije pronadjen"));
        model.addAttribute("lekarId", lekar.getId());
        model.addAttribute("danas", java.time.LocalDate.now().toString());
        return "index-lekar";
	    
	}

	
	@GetMapping("/termini")
	public String termini(@RequestParam(defaultValue = "false") boolean samoAktivni,
	                      Model model, Principal principal) {
	    Long lekarId = ls.currentLekarId(principal);
	    var lista = samoAktivni ? ts.aktivniZaLekara(lekarId) : ts.zaLekara(lekarId);

	    var ids = lista.stream().map(Termin::getId).toList();
	    var pregledi = ps.findByTermin_IdIn(ids);

	    Map<Long, Long> pregledIdByTermin = new HashMap<>();
	    for (var p : pregledi) {
	        if (p.getTermin() != null) {
	            pregledIdByTermin.put(p.getTermin().getId(), p.getId());
	        }
	    }

	    model.addAttribute("termini", lista);
	    model.addAttribute("pregledIdByTermin", pregledIdByTermin);
	    model.addAttribute("samoAktivni", samoAktivni);
	    model.addAttribute("lekarId", lekarId);
	    return "lekar-termini";
	}
	

	private Long currentLekarId(Principal p) {
		return ls.findByKorisnik_Username(p.getName())
				.map(Lekar::getId)
				.orElseThrow(() -> new IllegalStateException("Nije pronadjen lekar"));
	}
	
	@GetMapping("/pregledi/dan")
    public String terminiLekaraZaDan(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dan,
                                     Model model,
                                     Principal principal) {
		Long lekarId = currentLekarId(principal);
        var termini = ts.zaLekaraNaDan(lekarId, dan); 
        model.addAttribute("termini", termini);
        model.addAttribute("lekId", lekarId);
        model.addAttribute("dan", dan);
        return "preglediLekaraDan";
 }
}
