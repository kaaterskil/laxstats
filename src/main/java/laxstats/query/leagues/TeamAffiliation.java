package laxstats.query.leagues;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "team_affiliations", indexes = {
		@Index(name = "team_affiliation_idx1", columnList = "id, team_id, league_id"),
		@Index(name = "team_affiliation_idx2", columnList = "league_id"),
		@Index(name = "team_affiliation_idx3", columnList = "startingOn") }, uniqueConstraints = { @UniqueConstraint(name = "team_affiliation_uk1", columnNames = {
		"team_id", "league_id" }) })
public class TeamAffiliation implements Serializable {
	private static final long serialVersionUID = 4210935570475222296L;

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private TeamEntry team;

	@ManyToOne
	@JoinColumn(name = "league_id")
	private LeagueEntry league;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startingOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endingOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	/*---------- Constructors ----------*/

	public TeamAffiliation(TeamEntry team, LeagueEntry league) {
		this.team = team;
		this.league = league;
	}

	protected TeamAffiliation() {
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public void setTeam(TeamEntry team) {
		this.team = team;
	}

	public LeagueEntry getLeague() {
		return league;
	}

	public void setLeague(LeagueEntry league) {
		this.league = league;
	}

	public LocalDate getStartingOn() {
		return startingOn;
	}

	public void setStartingOn(LocalDate startingOn) {
		this.startingOn = startingOn;
	}

	public LocalDate getEndingOn() {
		return endingOn;
	}

	public void setEndingOn(LocalDate endingOn) {
		this.endingOn = endingOn;
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
