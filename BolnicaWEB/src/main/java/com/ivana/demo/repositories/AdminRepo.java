package com.ivana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Admin;

public interface AdminRepo  extends JpaRepository<Admin, Long>{

}
