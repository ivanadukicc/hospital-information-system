package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the pacijent database table.
 * 
 */
@Entity
@NamedQuery(name="Pacijent.findAll", query="SELECT p FROM Pacijent p")
public class Pacijent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String adresa;

	private String ime;

	private String jmbg;

	private String prezime;

	private String telefon;

	//bi-directional many-to-one association to Izabranilekar
	@OneToMany(mappedBy="pacijent")
	private List<Izabranilekar> izabranilekars;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	//bi-directional many-to-one association to Termin
	@OneToMany(mappedBy="pacijent")
	private List<Termin> termins;

	public Pacijent() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getJmbg() {
		return this.jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public List<Izabranilekar> getIzabranilekars() {
		return this.izabranilekars;
	}

	public void setIzabranilekars(List<Izabranilekar> izabranilekars) {
		this.izabranilekars = izabranilekars;
	}

	public Izabranilekar addIzabranilekar(Izabranilekar izabranilekar) {
		getIzabranilekars().add(izabranilekar);
		izabranilekar.setPacijent(this);

		return izabranilekar;
	}

	public Izabranilekar removeIzabranilekar(Izabranilekar izabranilekar) {
		getIzabranilekars().remove(izabranilekar);
		izabranilekar.setPacijent(null);

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
		termin.setPacijent(this);

		return termin;
	}

	public Termin removeTermin(Termin termin) {
		getTermins().remove(termin);
		termin.setPacijent(null);

		return termin;
	}

}