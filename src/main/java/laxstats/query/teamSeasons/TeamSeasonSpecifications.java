package laxstats.query.teamSeasons;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

public class TeamSeasonSpecifications {

	public static Specification<TeamSeasonEntry> findByGameDate(
			LocalDateTime date) {
		return new Specification<TeamSeasonEntry>() {

			@Override
			public Predicate toPredicate(Root<TeamSeasonEntry> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				final Predicate startsBefore = cb.lessThanOrEqualTo(
						root.get("season.startsOn"), date);
				final Predicate endsAfter = cb.greaterThanOrEqualTo(
						root.get("season.endsOn"), date);
				return cb.and(startsBefore, endsAfter);
			}
		};
	}
}
