package com.ivana.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Recept;

@Repository
public interface ReceptRepo extends JpaRepository<Recept, Long> {

	List<Recept> findByPregled_Termin_Pacijent_IdOrderByIzdatAtDesc(Long pacijentId);

	List<Recept> findByPregled_Termin_Lekar_IdOrderByIzdatAtDesc(Long lekarId);

	Recept findByPregled_Id(Long pregledId);

	Recept findByPregled_IdOrderByIzdatAtDesc(Integer intValue);

}
