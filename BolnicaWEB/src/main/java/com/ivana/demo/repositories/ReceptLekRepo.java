package com.ivana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.ReceptLek;
import model.ReceptLekPK;

@Repository
public interface ReceptLekRepo extends JpaRepository<ReceptLek, ReceptLekPK>{

}
