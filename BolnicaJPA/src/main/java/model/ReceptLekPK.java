package model;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the recept_lek database table.
 * 
 */
@Embeddable
public class ReceptLekPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="recept_id", insertable=false, updatable=false)
	private long receptId;

	@Column(name="lek_id", insertable=false, updatable=false)
	private long lekId;

	public ReceptLekPK() {
	}
	public long getReceptId() {
		return this.receptId;
	}
	public void setReceptId(long receptId) {
		this.receptId = receptId;
	}
	public long getLekId() {
		return this.lekId;
	}
	public void setLekId(long lekId) {
		this.lekId = lekId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReceptLekPK)) {
			return false;
		}
		ReceptLekPK castOther = (ReceptLekPK)other;
		return 
			(this.receptId == castOther.receptId)
			&& (this.lekId == castOther.lekId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.receptId ^ (this.receptId >>> 32)));
		hash = hash * prime + ((int) (this.lekId ^ (this.lekId >>> 32)));
		
		return hash;
	}
}