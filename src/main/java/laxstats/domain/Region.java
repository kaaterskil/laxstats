package laxstats.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(
	indexes = {
		@Index(name = "region_idx1", columnList = "region_name"),
		@Index(name = "region_idx2", columnList = "abbreviation")
	}
)
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@NotNull
	@Column(name = "region_name", length = 100, nullable = false)
	private String name;
	
	@Column(length = 2)
	private String abbreviation;
	
	//---------- Getter/Setters ----------//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}
