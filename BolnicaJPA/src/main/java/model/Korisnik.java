package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private byte aktivan;

	private String email;

	@Column(name="kreiran_at")
	private Timestamp kreiranAt;

	@Column(name="lozinka_hash")
	private String lozinkaHash;

	private String uloga;

	private String username;

	//bi-directional many-to-one association to Lekar
	@OneToMany(mappedBy="korisnik")
	private List<Lekar> lekars;

	//bi-directional many-to-one association to Pacijent
	@OneToMany(mappedBy="korisnik")
	private List<Pacijent> pacijents;

	//bi-directional many-to-one association to Admin
	@OneToMany(mappedBy="korisnik")
	private List<Admin> admins;

	public Korisnik() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getAktivan() {
		return this.aktivan;
	}

	public void setAktivan(byte aktivan) {
		this.aktivan = aktivan;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getKreiranAt() {
		return this.kreiranAt;
	}

	public void setKreiranAt(Timestamp kreiranAt) {
		this.kreiranAt = kreiranAt;
	}

	public String getLozinkaHash() {
		return this.lozinkaHash;
	}

	public void setLozinkaHash(String lozinkaHash) {
		this.lozinkaHash = lozinkaHash;
	}

	public String getUloga() {
		return this.uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Lekar> getLekars() {
		return this.lekars;
	}

	public void setLekars(List<Lekar> lekars) {
		this.lekars = lekars;
	}

	public Lekar addLekar(Lekar lekar) {
		getLekars().add(lekar);
		lekar.setKorisnik(this);

		return lekar;
	}

	public Lekar removeLekar(Lekar lekar) {
		getLekars().remove(lekar);
		lekar.setKorisnik(null);

		return lekar;
	}

	public List<Pacijent> getPacijents() {
		return this.pacijents;
	}

	public void setPacijents(List<Pacijent> pacijents) {
		this.pacijents = pacijents;
	}

	public Pacijent addPacijent(Pacijent pacijent) {
		getPacijents().add(pacijent);
		pacijent.setKorisnik(this);

		return pacijent;
	}

	public Pacijent removePacijent(Pacijent pacijent) {
		getPacijents().remove(pacijent);
		pacijent.setKorisnik(null);

		return pacijent;
	}

	public List<Admin> getAdmins() {
		return this.admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public Admin addAdmin(Admin admin) {
		getAdmins().add(admin);
		admin.setKorisnik(this);

		return admin;
	}

	public Admin removeAdmin(Admin admin) {
		getAdmins().remove(admin);
		admin.setKorisnik(null);

		return admin;
	}

}