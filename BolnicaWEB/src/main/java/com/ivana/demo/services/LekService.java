package com.ivana.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivana.demo.repositories.LekRepo;

import model.Lek;

@Service
public class LekService {

	@Autowired
	LekRepo lr;
	
	public List<Lek> getLeks() {
		return lr.findAll();
	}

	public List<Lek> findAll() {
		// TODO Auto-generated method stub
		return lr.findAll();
	}
}
