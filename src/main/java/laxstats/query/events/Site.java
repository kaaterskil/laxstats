package laxstats.query.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import laxstats.query.people.Address;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "site_idx1", columnList = "style"),
		@Index(name = "site_idx2", columnList = "surface")
	}
)
public class Site {
	
	public enum Style {
		COMPETITION, PRACTICE;
	}
	
	public enum Surface {
		GRASS, TURF;
	}

	@Id
	@Column(length = 36)
	private String id;
	
	@NotNull
	@Column(length = 100, nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Site.Style style;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Site.Surface surface;
	
	@OneToOne
	private Address address;
	
	@Column(columnDefinition = "text")
	private String directions;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	//---------- Getter/Setters ----------//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Site.Style getStyle() {
		return style;
	}

	public void setStyle(Site.Style style) {
		this.style = style;
	}

	public Site.Surface getSurface() {
		return surface;
	}

	public void setSurface(Site.Surface surface) {
		this.surface = surface;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
