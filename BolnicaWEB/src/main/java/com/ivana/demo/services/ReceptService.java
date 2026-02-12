package com.ivana.demo.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivana.demo.repositories.LekRepo;
import com.ivana.demo.repositories.PregledRepo;
import com.ivana.demo.repositories.ReceptLekRepo;
import com.ivana.demo.repositories.ReceptRepo;

import manager.Manager;
import model.Lek;
import model.Pregled;
import model.Recept;
import model.ReceptLek;
import model.ReceptLekPK;

@Service
public class ReceptService {

	@Autowired
	ReceptRepo rr;

	@Autowired
	ReceptLekRepo rlr;

	@Autowired
	LekRepo lr;

	@Autowired
	PregledRepo pr;

	public List<Recept> getRecepts() {
		return rr.findAll();
	}

	public List<Recept> getReceptePacijenta(long id) {
		return Manager.getReceptiPacijenta(id);
	}

	@Transactional
	public Recept izdaj(Long pregledId, String napomena, Long lekId, String doza, Integer kolicina, String trajanje,
			String ucestalost) {

		Pregled pregled = pr.findById(pregledId).orElseThrow(() -> new IllegalArgumentException("Pregled ne postoji"));

		Lek lek = lr.findById(lekId).orElseThrow(() -> new IllegalArgumentException("Lek ne postoji"));

		Recept r = rr.findByPregled_Id(pregledId);
		if (r == null) {
			r = new Recept();
			r.setPregled(pregled);
			r.setNapomena(napomena);
			r.setIzdatAt(Timestamp.from(Instant.now()));
			rr.save(r);

		}

		ReceptLek rl = new ReceptLek();
		ReceptLekPK pk = new ReceptLekPK();
		pk.setReceptId(r.getId());
		pk.setLekId(lek.getId());
		rl.setId(pk);

		rl.setRecept(r);
		rl.setLek(lek);

		rl.setDoza(doza);
		rl.setKolicina(kolicina != null ? kolicina : 1);
		rl.setTrajanje(trajanje);
		rl.setUcestalost(ucestalost);

		rlr.save(rl);
		return r;
	}
	
	@Transactional
	public List<Recept> zaPacijenta(Long pacijentId) {
		return rr.findByPregled_Termin_Pacijent_IdOrderByIzdatAtDesc(pacijentId);
	}

	public Recept findByPregled_Id(Long id) {
		return rr.findByPregled_Id(id);
	}

}
