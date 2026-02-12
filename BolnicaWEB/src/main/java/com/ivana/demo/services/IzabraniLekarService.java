package com.ivana.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivana.demo.repositories.IzabraniLekarRepo;
import com.ivana.demo.repositories.LekarRepo;
import com.ivana.demo.repositories.PacijentRepo;

import model.Izabranilekar;
import model.Lekar;
import model.Pacijent;

@Service
public class IzabraniLekarService {
	
	@Autowired
	IzabraniLekarRepo ilr;
	@Autowired
	LekarRepo lr;
	@Autowired
	PacijentRepo pr;
	
	public Optional<Izabranilekar> zaPacijenta(Long pacijentId) {
        return ilr.findByPacijentId(pacijentId);
    }

	
	@Transactional
	public void postavi(long pacijentId, Long lekarId) {
		Pacijent p = pr.findById(pacijentId).orElseThrow(() -> new IllegalArgumentException("Pacijent ne postoji"));
		Lekar l = lr.findById(lekarId).orElseThrow(() -> new IllegalArgumentException("Lekar ne postoji"));
		Izabranilekar il = ilr.findByPacijentId(pacijentId).orElseGet(Izabranilekar:: new);
		il.setPacijent(p);
		il.setLekar(l);
		ilr.save(il);
		
	}
	
	@Transactional
	public void ukloni(Long pacijentId) {
		ilr.findByPacijentId(pacijentId).ifPresent(ilr::delete);
	}
}
