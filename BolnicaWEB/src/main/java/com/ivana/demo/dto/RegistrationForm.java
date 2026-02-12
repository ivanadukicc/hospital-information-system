package com.ivana.demo.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegistrationForm {

	@NotBlank(message = "Ime je obavezno.")
    @Size(min = 2, max = 50, message = "Ime mora imati 2–50 znakova.")
	private String ime;
	
	@NotBlank(message = "Prezime je obavezno.")
    @Size(min = 2, max = 50, message = "Prezime mora imati 2–50 znakova.")
	private String prezime;
	
	@NotBlank(message = "Korisničko ime je obavezno.")
	@Size(min = 3, max = 30, message = "Korisničko ime mora imati 3–30 znakova.")
    private String username;
	
    @NotBlank(message = "JMBG je obavezan.")
    private String jmbg;
    
    @NotBlank(message = "Adresa je obavezna.")
    @Size(min = 5, max = 50, message = "Adresa mora imati 5–50 znakova.")
    private String adresa;
    
    @NotBlank(message = "Email je obavezan.")
    @Email(message = "Email format nije ispravan.")
    @Size(min = 5, max = 50, message = "Email mora imati 5-50 znakova")
    private String email;
    
    @NotBlank(message = "Telefon je obavezan.")
    @Size(max = 20, message = "Telefon je predugačak.")
    private String telefon;
    @NotBlank(message = "Lozinka je obavezna.")
    @Size(min = 5, max = 72, message = "Lozinka mora imati najmanje 5 znakova.")
    private String password;
    
    @NotBlank(message = "Potvrda lozinke je obavezna.")
    private String confirmPassword;
    
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
    
    public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getPassword() { 
    	return password; 
    	}
    public void setPassword(String password) { 
    	this.password = password; 
    	}
    public String getConfirmPassword() { 
    	return confirmPassword; 
    	}
    public void setConfirmPassword(String confirmPassword) { 
    	this.confirmPassword = confirmPassword; 
    	}
    
    @AssertTrue(message = "Lozinke se ne poklapaju.")
    public boolean isPasswordsMatch() {
        if (password == null || confirmPassword == null) return false;
        return password.equals(confirmPassword);
    }
}

