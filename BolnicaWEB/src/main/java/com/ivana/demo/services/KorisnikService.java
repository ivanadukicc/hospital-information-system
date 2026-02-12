package com.ivana.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivana.demo.dto.RegistrationForm;
import com.ivana.demo.repositories.KorisnikRepo;
import com.ivana.demo.repositories.PacijentRepo;

import jakarta.transaction.Transactional;
import model.Korisnik;
import model.Lekar;
import model.Pacijent;

@Service
public class KorisnikService {

	@Autowired
	KorisnikRepo kr;
	@Autowired
	PacijentRepo pr;
	@Autowired
	PasswordEncoder pe;

	public List<Korisnik> getKorisniks() {
		return kr.findAll();
	}

	public Korisnik getKorisnikById(Long korisnik) {
		return kr.findById(korisnik).get();
	}

	public boolean existsByUsername(String username) {
		return kr.existsByUsername(username);
	}

	public Korisnik findByUsername(String name) {
		return kr.findByUsername(name).orElseThrow(() -> new IllegalStateException("Korisnik ne postoji"));
	}

	@Transactional
	public Long registerPatient(RegistrationForm f) {
		if (f.getUsername() == null || f.getUsername().isBlank())
			throw new IllegalArgumentException("Korisničko ime je obavezno.");
		if (kr.existsByUsername(f.getUsername()))
			throw new IllegalArgumentException("Korisničko ime je zauzeto.");
		if (f.getPassword() == null || f.getPassword().length() < 6)
			throw new IllegalArgumentException("Lozinka mora imati bar 6 karaktera.");
		if (!f.getPassword().equals(f.getConfirmPassword()))
			throw new IllegalArgumentException("Lozinke se ne poklapaju.");
		if (f.getIme() == null || f.getIme().isBlank() || f.getPrezime() == null || f.getPrezime().isBlank())
			throw new IllegalArgumentException("Ime i prezime su obavezni.");

		Korisnik k = new Korisnik();
		k.setUsername(f.getUsername().trim());
		k.setEmail(f.getEmail()); 
		k.setLozinkaHash(pe.encode(f.getPassword()));
		k.setUloga("PACIJENT");
		k.setAktivan((byte) 1);                            
	    k.setKreiranAt(new java.sql.Timestamp(System.currentTimeMillis()));
		k = kr.save(k);

		Pacijent p = new Pacijent();
		p.setIme(f.getIme());
		p.setPrezime(f.getPrezime());
		p.setJmbg(f.getJmbg());
		p.setAdresa(f.getAdresa());
		p.setTelefon(f.getTelefon());
		p.setKorisnik(k);

		pr.save(p);

		return p.getId();

	}

}
