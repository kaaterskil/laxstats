package laxstats.query.teams;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "teams", indexes = {
		@Index(name = "team_idx1", columnList = "sponsor"),
		@Index(name = "team_idx2", columnList = "name"),
		@Index(name = "team_idx3", columnList = "abbreviation"),
		@Index(name = "team_idx4", columnList = "gender"),
		@Index(name = "team_idx5", columnList = "letter"),
		@Index(name = "team_idx6", columnList = "region") }, uniqueConstraints = { @UniqueConstraint(name = "team_uk1", columnNames = {
		"sponsor", "name", "gender", "letter" }) })
public class TeamEntry {

	@Id
	@Column(length = 36)
	private String id;

	@Column(length = 100, nullable = false)
	private String sponsor;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 5)
	private String abbreviation;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private TeamGender gender;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Letter letter;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Region region;

	@ManyToOne
	private LeagueEntry affiliation;

	@ManyToOne
	private SiteEntry homeSite;

	@Column(length = 50)
	private String encodedPassword;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private final List<TeamSeasonEntry> seasons = new ArrayList<>();

	/*---------- Methods ----------*/

	public String getFullName() {
		final StringBuilder sb = new StringBuilder();

		sb.append(sponsor);
		if (name != null) {
			sb.append(" ").append(name);
		}
		return sb.toString();
	}

	public String getTitle() {
		final StringBuilder sb = new StringBuilder();
		sb.append(sponsor).append(" ").append(gender.getLabel()).append("'s ")
				.append(letter.getLabel());
		return sb.toString();
	}

	public TeamSeasonEntry getSeason(String id) {
		TeamSeasonEntry result = null;
		for (final TeamSeasonEntry each : seasons) {
			if (each.getId().equals(id)) {
				result = each;
				break;
			}
		}
		return result;
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

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

	public TeamGender getGender() {
		return gender;
	}

	public void setGender(TeamGender gender) {
		this.gender = gender;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public LeagueEntry getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(LeagueEntry affiliation) {
		this.affiliation = affiliation;
	}

	public SiteEntry getHomeSite() {
		return homeSite;
	}

	public void setHomeSite(SiteEntry homeSite) {
		this.homeSite = homeSite;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
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

	public List<TeamSeasonEntry> getSeasons() {
		return seasons;
	}
}
