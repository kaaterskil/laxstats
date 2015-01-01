package laxstats.query.relationships;

import org.springframework.data.repository.CrudRepository;

public interface RelationshipQueryRepository extends
		CrudRepository<RelationshipEntry, String> {

}
