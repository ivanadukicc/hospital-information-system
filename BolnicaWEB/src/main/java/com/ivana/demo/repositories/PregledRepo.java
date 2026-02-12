package com.ivana.demo.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Pregled;

@Repository
public interface PregledRepo extends JpaRepository<Pregled, Long>{
	
	Optional<Pregled> findByTermin_IdOrderByIdDesc(Long terminId);

	Optional<Pregled> findByTermin_Id(Long terminId);
	List<Pregled> findByTerminIdIn(Collection<Long> terminIds);
	

}
