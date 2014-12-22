package laxstats.domain.leagues;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.leagues.AffiliateLeagueRegisteredEvent;
import laxstats.api.leagues.LeagueCreatedEvent;
import laxstats.api.leagues.LeagueDTO;
import laxstats.api.leagues.LeagueDeletedEvent;
import laxstats.api.leagues.LeagueId;
import laxstats.api.leagues.LeagueUpdatedEvent;
import laxstats.api.leagues.TeamRegisteredEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class League extends AbstractAnnotatedAggregateRoot<LeagueId> {
	private static final long serialVersionUID = -4478680930644845338L;

	@AggregateIdentifier
	private LeagueId id;
	private LeagueInfo leagueInfo;
	private String parentLeague;
	private final List<String> affiliatedLeagues = new ArrayList<>();
	private final List<TeamInfo> teams = new ArrayList<>();

	public League(LeagueId leagueId, LeagueDTO leagueDTO) {
		apply(new LeagueCreatedEvent(leagueId, leagueDTO));
	}

	protected League() {
	}

	// ---------- Methods ----------//

	public void update(LeagueId leagueId, LeagueDTO leagueDTO) {
		apply(new LeagueUpdatedEvent(leagueId, leagueDTO));
	}

	public void delete(LeagueId leagueId) {
		if (teams.size() > 0) {
			throw new IllegalArgumentException(
					"cannot delete league with registered teams");
		}
		if (affiliatedLeagues.size() > 0) {
			throw new IllegalArgumentException(
					"cannot delete league with registered affiliates");
		}
		apply(new LeagueDeletedEvent(leagueId));
	}

	public void registerAffiliate(String affiliateId) {
		if (!canAcceptAffiliate(affiliateId)) {
			throw new IllegalArgumentException("Invalid child league");
		}
		apply(new AffiliateLeagueRegisteredEvent(id, affiliateId));
	}

	private boolean canAcceptAffiliate(String affiliateId) {
		if (affiliatedLeagues.contains(affiliateId)) {
			return false;
		}
		return true;
	}

	public void registerTeam(TeamInfo team) {
		if (!canLeagueAcceptTeam(team)) {
			throw new IllegalArgumentException("Invalid team");
		}
		apply(new TeamRegisteredEvent(id, team));
	}

	private boolean canLeagueAcceptTeam(TeamInfo team) {
		if (teams.contains(team) || affiliatedLeagues.size() > 0) {
			return false;
		}
		return true;
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(LeagueCreatedEvent event) {
		final LeagueDTO dto = event.getLeagueDTO();

		final LeagueInfo leagueInfo = new LeagueInfo(dto.getName(),
				dto.getLevel(), dto.getDescription());

		this.id = event.getLeagueId();
		this.leagueInfo = leagueInfo;
		if (dto.getParent() != null) {
			this.parentLeague = dto.getParent().toString();
		}
	}

	@EventSourcingHandler
	protected void handle(LeagueUpdatedEvent event) {
		final LeagueDTO dto = event.getLeagueDTO();

		final LeagueInfo leagueInfo = new LeagueInfo(dto.getName(),
				dto.getLevel(), dto.getDescription());

		this.leagueInfo = leagueInfo;
		if (dto.getParent() != null) {
			this.parentLeague = dto.getParent().toString();
		}
	}

	@EventSourcingHandler
	protected void handle(LeagueDeletedEvent event) {
		markDeleted();
	}

	@EventSourcingHandler
	protected void handle(AffiliateLeagueRegisteredEvent event) {
		final String affiliateId = event.getAffiliateId();
		affiliatedLeagues.add(affiliateId);
	}

	@EventSourcingHandler
	protected void handle(TeamRegisteredEvent event) {
		final TeamInfo team = event.getTeam();
		teams.add(team);
	}

	// ---------- Getters ----------//

	@Override
	public LeagueId getIdentifier() {
		return id;
	}

	public LeagueInfo getLeagueInfo() {
		return leagueInfo;
	}

	public String getParentLeague() {
		return parentLeague;
	}

	public List<String> getAffiliatedLeagues() {
		return affiliatedLeagues;
	}

	public List<TeamInfo> getTeams() {
		return teams;
	}
}
