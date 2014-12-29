package laxstats.query.players;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.query.people.PersonEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "players", indexes = {
		@Index(name = "player_idx1", columnList = "id, team_season, person"),
		@Index(name = "player_idx2", columnList = "team_season"),
		@Index(name = "player_idx3", columnList = "person"),
		@Index(name = "player_idx4", columnList = "role"),
		@Index(name = "player_idx5", columnList = "status"),
		@Index(name = "player_idx6", columnList = "isCaptain"),
		@Index(name = "player_idx7", columnList = "depth") }, uniqueConstraints = { @UniqueConstraint(name = "player_uk1", columnNames = {
		"id", "team_season", "person" }) })
public class PlayerEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonEntry person;

	@ManyToOne
	@JoinColumn(nullable = false)
	private TeamSeasonEntry teamSeason;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private PlayerStatus status;

	@Column(length = 4, nullable = false)
	private String jerseyNumber;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Position position;

	private boolean isCaptain;
	private int depth;
	private int height;
	private int weight;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	// ---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public void setPerson(PersonEntry person) {
		this.person = person;
	}

	public TeamSeasonEntry getTeamSeason() {
		return teamSeason;
	}

	public void setTeamSeason(TeamSeasonEntry teamSeason) {
		this.teamSeason = teamSeason;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
