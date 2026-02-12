package com.ivana.demo.dto;

public class SpecMesecStatDTO {

	private String specijalnost; 
    private String mesec;          
    private Long brojTermina;

    public SpecMesecStatDTO(String specijalnost, String mesec, Long brojTermina) {
        this.specijalnost = specijalnost;
        this.mesec = mesec;
        this.brojTermina = brojTermina;
    }
    public String getSpecijalnost() { 
    	return specijalnost; 
    	}
    public String getMesec() { 
    	return mesec; 
    	}
    public Long getBrojTermina() { 
    	return brojTermina; 
    	}
}
