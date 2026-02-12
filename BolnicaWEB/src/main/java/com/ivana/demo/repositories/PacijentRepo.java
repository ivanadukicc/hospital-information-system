package com.ivana.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Pacijent;

@Repository
public interface PacijentRepo extends JpaRepository<Pacijent, Long> {
	Optional<Pacijent> findByKorisnik_Username(String username);
	Optional<Pacijent> findByKorisnik_Id(Long korisnikId);

}
