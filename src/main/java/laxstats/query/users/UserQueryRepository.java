package laxstats.query.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserQueryRepository extends PagingAndSortingRepository<UserEntry, String> {

   Iterable<UserEntry> findAllByOrderByEmailAsc();

   Iterable<UserEntry> findAllByOrderByLastNameAsc();

   @Query(value = "select * from users u where ?1 is not null and u.team_id = cast(?1 as text) "
      + "order by u.last_name", nativeQuery = true)
   Iterable<UserEntry> findByTeamOrderByLastNameAsc(String teamId);

   UserEntry findByEmail(String email);

   UserEntry findById(String id);

   @Query("select count (*) from UserEntry ue where upper(ue.email) = upper(cast(?1 as text))")
   int uniqueEmail(String email);

   @Query("select count(*) from UserEntry ue "
      + "where ?1 is not null and upper(ue.email) = upper(cast(?1 as text)) and ue.id <> ?2")
   int updateEmail(String email, String id);

   @Query("select count(*) from PlayEntry pe where $1 is not null and "
      + "(pe.createdBy.id = cast(?1 as text) or pe.modifiedBy.id = cast(?1 as text))")
   int countPlaysCreatedOrModified(String id);

   @Query("select count(*) from GameEntry ge where $1 is not null and "
      + "(ge.createdBy.id = cast(?1 as text) or ge.modifiedBy.id = cast(?1 as text))")
   int countGamesCreatedOrModified(String id);
}
