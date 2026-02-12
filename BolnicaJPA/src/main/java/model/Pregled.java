package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the pregled database table.
 * 
 */
@Entity
@NamedQuery(name="Pregled.findAll", query="SELECT p FROM Pregled p")
public class Pregled implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String dijagnoza;

	@Column(name="kreiran_at")
	private Timestamp kreiranAt;

	@Lob
	private String preporuke;

	@OneToOne
	@JoinColumn(name = "termin_id", nullable = false, unique = true)
	private Termin termin;
	//bi-directional many-to-one association to Recept
	@OneToMany(mappedBy="pregled")
	private List<Recept> recepts;

	public Pregled() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDijagnoza() {
		return this.dijagnoza;
	}

	public void setDijagnoza(String dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Timestamp getKreiranAt() {
		return this.kreiranAt;
	}

	public void setKreiranAt(Timestamp kreiranAt) {
		this.kreiranAt = kreiranAt;
	}

	public String getPreporuke() {
		return this.preporuke;
	}

	public void setPreporuke(String preporuke) {
		this.preporuke = preporuke;
	}

	public Termin getTermin() {
		return this.termin;
	}

	public void setTermin(Termin termin) {
		this.termin = termin;
	}

	public List<Recept> getRecepts() {
		return this.recepts;
	}

	public void setRecepts(List<Recept> recepts) {
		this.recepts = recepts;
	}

	public Recept addRecept(Recept recept) {
		getRecepts().add(recept);
		recept.setPregled(this);

		return recept;
	}

	public Recept removeRecept(Recept recept) {
		getRecepts().remove(recept);
		recept.setPregled(null);

		return recept;
	}

}