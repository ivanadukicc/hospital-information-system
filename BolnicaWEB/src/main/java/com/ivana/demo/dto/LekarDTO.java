package com.ivana.demo.dto;

import jakarta.validation.constraints.*;

public class LekarDTO {
	@NotBlank
	private String ime;
	@NotBlank
	private String prezime;
	@NotBlank
	private String username;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String lozinka; 
	@NotBlank
	private String specijalnost; 
	private long brojTermina;
	
	public LekarDTO(String ime, String prezime, long brojTermina) {
		// TODO Auto-generated constructor stub
		this.ime = ime;
		this.prezime = prezime;
		this.brojTermina = brojTermina;
	}
	public LekarDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getSpecijalnost() {
		return specijalnost;
	}
	public void setSpecijalnost(String specijalnost) {
		this.specijalnost = specijalnost;
	}
	public long getBrojTermina() {
		return brojTermina;
	}
	public void setBrojTermina(long brojTermina) {
		this.brojTermina = brojTermina;
	}
	
	

}
