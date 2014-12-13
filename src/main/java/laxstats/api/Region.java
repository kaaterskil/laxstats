package laxstats.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(
	indexes = {
		@Index(name = "region_idx1", columnList = "name")
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "region_uk1", columnNames = {"abbreviation"})
	}
)
public class Region {

	@Id
	@Column(length = 36)
	private String id;
	
	@NotNull
	@Column(length = 100, nullable = false)
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
