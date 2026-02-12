package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the recept database table.
 * 
 */
@Entity
@NamedQuery(name="Recept.findAll", query="SELECT r FROM Recept r")
public class Recept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="izdat_at")
	private Timestamp izdatAt;

	private String napomena;

	//bi-directional many-to-one association to Pregled
	@ManyToOne
	private Pregled pregled;

	//bi-directional many-to-one association to ReceptLek
	@OneToMany(mappedBy="recept")
	private List<ReceptLek> receptLeks;

	public Recept() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getIzdatAt() {
		return this.izdatAt;
	}

	public void setIzdatAt(Timestamp izdatAt) {
		this.izdatAt = izdatAt;
	}

	public String getNapomena() {
		return this.napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public Pregled getPregled() {
		return this.pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public List<ReceptLek> getReceptLeks() {
		return this.receptLeks;
	}

	public void setReceptLeks(List<ReceptLek> receptLeks) {
		this.receptLeks = receptLeks;
	}

	public ReceptLek addReceptLek(ReceptLek receptLek) {
		getReceptLeks().add(receptLek);
		receptLek.setRecept(this);

		return receptLek;
	}

	public ReceptLek removeReceptLek(ReceptLek receptLek) {
		getReceptLeks().remove(receptLek);
		receptLek.setRecept(null);

		return receptLek;
	}

}