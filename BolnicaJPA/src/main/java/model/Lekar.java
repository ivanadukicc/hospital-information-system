package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the lekar database table.
 * 
 */
@Entity
@NamedQuery(name="Lekar.findAll", query="SELECT l FROM Lekar l")
public class Lekar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String ime;

	private String prezime;

	private String specijalnost;

	//bi-directional many-to-one association to Izabranilekar
	@OneToMany(mappedBy="lekar")
	private List<Izabranilekar> izabranilekars;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	//bi-directional many-to-one association to Termin
	@OneToMany(mappedBy="lekar")
	private List<Termin> termins;

	public Lekar() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getSpecijalnost() {
		return this.specijalnost;
	}

	public void setSpecijalnost(String specijalnost) {
		this.specijalnost = specijalnost;
	}

	public List<Izabranilekar> getIzabranilekars() {
		return this.izabranilekars;
	}

	public void setIzabranilekars(List<Izabranilekar> izabranilekars) {
		this.izabranilekars = izabranilekars;
	}

	public Izabranilekar addIzabranilekar(Izabranilekar izabranilekar) {
		getIzabranilekars().add(izabranilekar);
		izabranilekar.setLekar(this);

		return izabranilekar;
	}

	public Izabranilekar removeIzabranilekar(Izabranilekar izabranilekar) {
		getIzabranilekars().remove(izabranilekar);
		izabranilekar.setLekar(null);

		return izabranilekar;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<Termin> getTermins() {
		return this.termins;
	}

	public void setTermins(List<Termin> termins) {
		this.termins = termins;
	}

	public Termin addTermin(Termin termin) {
		getTermins().add(termin);
		termin.setLekar(this);

		return termin;
	}

	public Termin removeTermin(Termin termin) {
		getTermins().remove(termin);
		termin.setLekar(null);

		return termin;
	}

}