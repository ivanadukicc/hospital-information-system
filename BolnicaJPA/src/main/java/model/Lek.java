package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the lek database table.
 * 
 */
@Entity
@NamedQuery(name="Lek.findAll", query="SELECT l FROM Lek l")
public class Lek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String jacina;

	private String naziv;

	private String oblik;

	//bi-directional many-to-one association to ReceptLek
	@OneToMany(mappedBy="lek")
	private List<ReceptLek> receptLeks;

	public Lek() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJacina() {
		return this.jacina;
	}

	public void setJacina(String jacina) {
		this.jacina = jacina;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOblik() {
		return this.oblik;
	}

	public void setOblik(String oblik) {
		this.oblik = oblik;
	}

	public List<ReceptLek> getReceptLeks() {
		return this.receptLeks;
	}

	public void setReceptLeks(List<ReceptLek> receptLeks) {
		this.receptLeks = receptLeks;
	}

	public ReceptLek addReceptLek(ReceptLek receptLek) {
		getReceptLeks().add(receptLek);
		receptLek.setLek(this);

		return receptLek;
	}

	public ReceptLek removeReceptLek(ReceptLek receptLek) {
		getReceptLeks().remove(receptLek);
		receptLek.setLek(null);

		return receptLek;
	}

}