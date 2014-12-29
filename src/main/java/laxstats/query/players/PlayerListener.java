package laxstats.query.players;

import laxstats.api.players.PlayerCreatedEvent;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerDeletedEvent;
import laxstats.api.players.PlayerUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerListener {
	private PlayerQueryRepository repository;

	@Autowired
	public void setRepository(PlayerQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(PlayerCreatedEvent event) {
		final PlayerDTO dto = event.getPlayerDTO();
		final String id = event.getPlayerId().toString();

		final PlayerEntry entity = new PlayerEntry();
		entity.setId(id);
		entity.setPerson(dto.getPerson());
		entity.setTeamSeason(dto.getTeam());
		entity.setRole(dto.getRole());
		entity.setStatus(dto.getStatus());
		entity.setJerseyNumber(dto.getJerseyNumber());
		entity.setPosition(dto.getPosition());
		entity.setCaptain(dto.isCaptain());
		entity.setDepth(dto.getDepth());
		entity.setHeight(dto.getHeight());
		entity.setWeight(dto.getWeight());
		entity.setCreatedAt(dto.getCreatedAt());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		repository.save(entity);
	}

	@EventHandler
	protected void handle(PlayerUpdatedEvent event) {
		final PlayerDTO dto = event.getPlayerDTO();
		final String id = event.getPlayerId().toString();

		final PlayerEntry entity = repository.findOne(id);
		entity.setPerson(dto.getPerson());
		entity.setTeamSeason(dto.getTeam());
		entity.setRole(dto.getRole());
		entity.setStatus(dto.getStatus());
		entity.setJerseyNumber(dto.getJerseyNumber());
		entity.setPosition(dto.getPosition());
		entity.setCaptain(dto.isCaptain());
		entity.setDepth(dto.getDepth());
		entity.setHeight(dto.getHeight());
		entity.setWeight(dto.getWeight());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		repository.save(entity);
	}

	@EventHandler
	protected void handle(PlayerDeletedEvent event) {
		final String id = event.getPlayerId().toString();
		final PlayerEntry entity = repository.findOne(id);
		repository.delete(entity);
	}
}
