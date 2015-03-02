package laxstats.web.teams;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;

public class TeamForm {

	String id;

	@NotNull
	@Size(min = 5, max = 100)
	private String sponsor;

	@NotNull
	@Size(min = 3, max = 100)
	private String name;

	@Size(max = 5)
	private String abbreviation;

	@NotNull
	private TeamGender gender;

	@NotNull
	private Letter letter;

	@NotNull
	private Region region;

	private String homeSite;
	private String affiliation;

	private List<TeamGender> genders;
	private List<Letter> letters;
	private List<SiteEntry> sites;
	private List<Region> regions;
	private List<LeagueEntry> affiliations;

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

	public String getHomeSite() {
		return homeSite;
	}

	public void setHomeSite(String homeSite) {
		this.homeSite = homeSite;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	/*---------- Drop down lists ----------*/

	public List<TeamGender> getGenders() {
		if (genders == null) {
			genders = Arrays.asList(TeamGender.values());
		}
		return genders;
	}

	public List<Letter> getLetters() {
		if (letters == null) {
			letters = Arrays.asList(Letter.values());
		}
		return letters;
	}

	public List<Region> getRegions() {
		if (regions == null) {
			regions = Arrays.asList(Region.values());
		}
		return regions;
	}

	public void setSites(List<SiteEntry> sites) {
		this.sites = sites;
	}

	public List<SiteEntry> getSites() {
		return sites;
	}

	public List<LeagueEntry> getAffiliations() {
		return affiliations;
	}

	public void setAffiliations(List<LeagueEntry> affiliations) {
		this.affiliations = affiliations;
	}
}
