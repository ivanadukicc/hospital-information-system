package com.ivana.demo.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivana.demo.dto.LekarDTO;
import com.ivana.demo.dto.SpecMesecStatDTO;
import com.ivana.demo.repositories.KorisnikRepo;
import com.ivana.demo.repositories.LekarRepo;
import com.ivana.demo.repositories.TerminRepo;

import model.Korisnik;
import model.Lekar;
import model.Termin;

@Service
public class AdminService {

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	KorisnikRepo kr;
	@Autowired
	LekarRepo lr;
	@Autowired
	TerminRepo tr;

	private static boolean isNullOrBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	@Transactional
	public Lekar kreirajNovogLekara(LekarDTO dto) {

		Objects.requireNonNull(dto, "LekarDTO je obavezan");
		if (isNullOrBlank(dto.getIme()))
			throw new IllegalArgumentException("Ime je obavezno");
		if (isNullOrBlank(dto.getPrezime()))
			throw new IllegalArgumentException("Prezime je obavezno");
		if (isNullOrBlank(dto.getUsername()))
			throw new IllegalArgumentException("Username je obavezan");
		if (isNullOrBlank(dto.getEmail()))
			throw new IllegalArgumentException("Email je obavezan");
		if (isNullOrBlank(dto.getLozinka()))
			throw new IllegalArgumentException("Lozinka je obavezna");
		if (isNullOrBlank(dto.getSpecijalnost()))
			throw new IllegalArgumentException("Specijalnost je obavezna");


		Korisnik k = new Korisnik();
		k.setUsername(dto.getUsername());
		k.setEmail(dto.getEmail());
		k.setLozinkaHash(encoder.encode(dto.getLozinka()));
		k.setUloga("LEKAR");
		k.setAktivan((byte) 1);
		k.setKreiranAt(Timestamp.from(Instant.now()));

		kr.save(k);

		Lekar l = new Lekar();
		l.setKorisnik(k);
		l.setIme(dto.getIme().trim());
		l.setPrezime(dto.getPrezime().trim());
		l.setSpecijalnost(dto.getSpecijalnost().trim());
		return lr.save(l);
	}
	
	  public Page<Termin> pretragaTermina(Long lekarId, LocalDateTime od, LocalDateTime doo, int page, int size) {
	        return tr.pretraga(lekarId, od, doo,
	                PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by("pocetak").descending()));
	    }
	  
	
	  public long brojTerminaZaDan(LocalDate dan) {
	        LocalDateTime start = dan.atStartOfDay();
	        LocalDateTime end = dan.plusDays(1).atStartOfDay();
	        return tr.brojTerminaZaDan(start, end);
	    }
	  
	  public List<LekarDTO> rangLekara(LocalDate od, LocalDate doo) {
		  LocalDateTime start = (od != null) ? od.atStartOfDay() : null;
		  LocalDateTime end   = (doo != null) ? doo.plusDays(1).atStartOfDay() : null;
		  List<Object[]> rows = tr.rangLekara(start, end);
		  List<LekarDTO> result = new ArrayList<>();
		  for (Object[] r : rows) {
		        String ime = String.valueOf(r[1]);
		        String prezime = String.valueOf(r[2]);
		        long brojTermina = ((Number) r[3]).longValue();

		        LekarDTO dto = new LekarDTO(ime, prezime, brojTermina);
		        result.add(dto);
		    }

		    return result;

	       
	    }
	  public List<SpecMesecStatDTO> specStatMesecno() {
	        List<Object[]> rows = tr.specStatMesecno();
	        List<SpecMesecStatDTO> result = new ArrayList<>();
	        for(Object[] r : rows) {
	        	String specijalnost = String.valueOf(r[0]);
	        	String mesec = String.valueOf(r[1]);
	        	long brojTermina = ((Number) r[2]).longValue();
	        	SpecMesecStatDTO dto = new SpecMesecStatDTO(specijalnost, mesec, brojTermina);
	        	result.add(dto);
	        }
	        return result;
	        
	    }
	  
	  

}
