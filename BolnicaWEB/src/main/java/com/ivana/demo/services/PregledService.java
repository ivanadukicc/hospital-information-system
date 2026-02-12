package com.ivana.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivana.demo.repositories.PregledRepo;
import manager.Manager;
import model.Pregled;

@Service
public class PregledService {

	@Autowired
	PregledRepo pr;
	@Autowired
	TerminService ts;

	public List<Pregled> getPregledePacijenta(long id, LocalDate od, LocalDate _do) {
		return Manager.getPreglediPacijenta(id, od, _do);

	}

	public List<Pregled> getPregledeLekara(long id, LocalDate od, LocalDate _do) {
		return Manager.getPreglediLekara(id, od, _do);
	}

	public List<Pregled> findByTermin_IdIn(List<Long> ids) {

		return pr.findByTerminIdIn(ids);
	}

	public Optional<Pregled> findById(Long pregledId) {
		return pr.findById(pregledId);
	}

	public Pregled save(Pregled pregled) {
		return pr.save(pregled);
		
	}

	 @Transactional
	    public Pregled kreirajPregled(Long terminId, String dijagnoza, String preporuke) {
	        var termin = ts.findById(terminId)
	                .orElseThrow(() -> new IllegalArgumentException("Termin ne postoji"));

	        if (termin.getPregled() != null) {
	            return termin.getPregled();
	        }

	        Pregled p = new Pregled();
	        p.setTermin(termin);
	        p.setDijagnoza(dijagnoza);
	        p.setPreporuke(preporuke == null ? "" : preporuke);
	        p.setKreiranAt(new java.sql.Timestamp(System.currentTimeMillis()));

	        return pr.save(p);
	    }


}
