package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the recept_lek database table.
 * 
 */
@Entity
@Table(name="recept_lek")
@NamedQuery(name="ReceptLek.findAll", query="SELECT r FROM ReceptLek r")
public class ReceptLek implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReceptLekPK id;

	private String doza;

	private int kolicina;

	private String trajanje;

	private String ucestalost;

	//bi-directional many-to-one association to Lek
	@ManyToOne
	 @MapsId("lekId") // mapira id.lekId na ovu vezu
    @JoinColumn(name = "lek_id")
	private Lek lek;

	//bi-directional many-to-one association to Recept
	@ManyToOne
	 @MapsId("receptId") 
    @JoinColumn(name = "recept_id")
    private Recept recept;

	public ReceptLek() {
	}

	public ReceptLekPK getId() {
		return this.id;
	}

	public void setId(ReceptLekPK id) {
		this.id = id;
	}

	public String getDoza() {
		return this.doza;
	}

	public void setDoza(String doza) {
		this.doza = doza;
	}

	public int getKolicina() {
		return this.kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public String getTrajanje() {
		return this.trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	public String getUcestalost() {
		return this.ucestalost;
	}

	public void setUcestalost(String ucestalost) {
		this.ucestalost = ucestalost;
	}

	public Lek getLek() {
		return this.lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public Recept getRecept() {
		return this.recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

}