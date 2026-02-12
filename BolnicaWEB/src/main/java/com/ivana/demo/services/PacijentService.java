package com.ivana.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivana.demo.repositories.PacijentRepo;
import model.Pacijent;

@Service
public class PacijentService {
	
	@Autowired
	PacijentRepo pr;
	
	public List<Pacijent> getPacijents() {
		return pr.findAll();
	}
	
	public Optional<Pacijent> findByKorisnikUsername(String username) {
		return pr.findByKorisnik_Username(username);
	}

}
