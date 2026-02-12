package com.ivana.demo.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivana.demo.dto.LekarDTO;

import model.Lekar;

@Repository
public interface LekarRepo extends JpaRepository<Lekar, Long> {

	  List<Lekar> findBySpecijalnostContainingIgnoreCase(String q);
	  Optional<Lekar> findByKorisnik_Username(String username);
	  Optional<Lekar> findByKorisnik_Id(Long korisnikId);
	  
	  
	  @Query("SELECT new com.ivana.demo.dto.LekarDTO(l.ime, l.prezime, COUNT(t)) " +
		       "FROM Lekar l JOIN l.termins t " +
		       "GROUP BY l.id, l.ime, l.prezime")
		List<LekarDTO> brojTerminaPoLekaru();
}
