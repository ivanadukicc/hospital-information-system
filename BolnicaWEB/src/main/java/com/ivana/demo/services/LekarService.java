package com.ivana.demo.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivana.demo.repositories.LekarRepo;

import model.Lekar;


@Service
public class LekarService {

	@Autowired
	LekarRepo lr;
	@Autowired
	KorisnikService ks;

	public List<Lekar> getLekars() {
		return lr.findAll();
	}

	public List<Lekar> findBySpecijalnost(String q) {
		if (q == null) {
			return lr.findAll();
		}
		String s = q.trim();
		if (s.isEmpty()) {
			return lr.findAll();
		}
		return lr.findBySpecijalnostContainingIgnoreCase(s);

	}

	public Optional<Lekar> findOne(Long id) {
		if (id == null) {
			return Optional.empty();
		}
		return lr.findById(id);
	}
	public Lekar save(Lekar lekar) {
		return lr.save(lekar);
	}
	
	public void delete(Long id) {
		lr.deleteById(id);
	}
	
	public Long currentLekarId(Principal principal) {
	    return lr.findByKorisnik_Username(principal.getName())
	    		.map(Lekar::getId)
	    		.orElseThrow(() -> new IllegalStateException("Lekar nije pronadjen"));
	}

	public Optional<Lekar> findByKorisnik_Username(String name) {
		return lr.findByKorisnik_Username(name);
	}

}
