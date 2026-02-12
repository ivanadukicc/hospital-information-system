package com.ivana.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Korisnik;

@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {
	Optional<Korisnik> findByUsername(String username);
	boolean existsByUsername(String username);

}
