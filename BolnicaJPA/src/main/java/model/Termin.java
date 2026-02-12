package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the termin database table.
 * 
 */
@Entity
@NamedQuery(name="Termin.findAll", query="SELECT t FROM Termin t")
public class Termin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date kraj;

	private String napomena;

	@Temporal(TemporalType.TIMESTAMP)
	private Date pocetak;

	private String status;

	@OneToOne(mappedBy = "termin") 
	private Pregled pregled;
	
	//bi-directional many-to-one association to Lekar
	@ManyToOne
	private Lekar lekar;

	//bi-directional many-to-one association to Pacijent
	@ManyToOne
	private Pacijent pacijent;

	public Termin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getKraj() {
		return this.kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	public String getNapomena() {
		return this.napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public Date getPocetak() {
		return this.pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Pregled getPregled() {
		return this.pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Lekar getLekar() {
		return this.lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return this.pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

}