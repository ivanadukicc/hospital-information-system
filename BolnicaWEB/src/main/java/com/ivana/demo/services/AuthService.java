package com.ivana.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ivana.demo.repositories.KorisnikRepo;

import model.Korisnik;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	KorisnikRepo kr;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik k = kr.findByUsername(username).orElseThrow();
		if(k == null) {
			throw new UsernameNotFoundException("Nepoznat korisnik" + username);
		}
		String role = "ROLE_" + (k.getUloga() == null ? "PACIJENT" : k.getUloga().toUpperCase());
		return User.builder()
				.username(k.getUsername())
				.password(k.getLozinkaHash())
				.authorities(role)
				.accountLocked(false)
				.disabled(false)
				.build();
	}

	
	
	
	

}
