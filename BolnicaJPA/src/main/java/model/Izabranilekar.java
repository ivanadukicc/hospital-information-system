package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the izabranilekar database table.
 * 
 */
@Entity
@NamedQuery(name="Izabranilekar.findAll", query="SELECT i FROM Izabranilekar i")
public class Izabranilekar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	//bi-directional many-to-one association to Lekar
	@ManyToOne
	private Lekar lekar;

	//bi-directional many-to-one association to Pacijent
	@ManyToOne
	private Pacijent pacijent;

	public Izabranilekar() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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