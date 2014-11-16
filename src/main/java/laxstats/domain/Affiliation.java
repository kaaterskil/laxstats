package laxstats.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "affiliation_idx1", columnList = "level")})
public class Affiliation {
	
	public enum Level {
		LEAGUE, DIVISION, CONFERENCE;
	}

	@Id
	@Column(length = 36)
	private String id;
	
	@NotNull
	@Column(length = 50, nullable = false)
	private String name;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Affiliation.Level level;
	
	@ManyToOne
	private Affiliation parent;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne(targetEntity = User.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = User.class)
	private String modifiedBy;
	
	@OneToMany(mappedBy = "parent")
	private Set<Affiliation> children = new HashSet<Affiliation>();
	
	@OneToMany(mappedBy = "affiliation")
	private Set<TeamAffiliation> affiliatedTeams = new HashSet<TeamAffiliation>();
	
	//---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Affiliation getParent() {
		return parent;
	}

	public void setParent(Affiliation parent) {
		this.parent = parent;
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

	public Set<Affiliation> getChildren() {
		return children;
	}

	public void setChildren(Set<Affiliation> children) {
		this.children = children;
	}
	
	public boolean addChild(Affiliation child) {
		child.setParent(this);
		return children.add(child);
	}

	public Set<TeamAffiliation> getAffiliatedTeams() {
		return affiliatedTeams;
	}
}
