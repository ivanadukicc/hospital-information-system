package com.ivana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Lek;

@Repository
public interface LekRepo extends JpaRepository<Lek, Long> {

}
