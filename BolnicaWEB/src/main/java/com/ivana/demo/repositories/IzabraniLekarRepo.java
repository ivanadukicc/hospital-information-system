package com.ivana.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Izabranilekar;

public interface IzabraniLekarRepo extends JpaRepository<Izabranilekar, Long> {
	Optional<Izabranilekar> findByPacijentId(Long pacijentId);
	boolean existsByPacijentId(Long pacijentId);

}
