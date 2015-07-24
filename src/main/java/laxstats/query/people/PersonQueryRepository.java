package laxstats.query.people;

import java.util.List;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.people.ContactMethod;
import laxstats.api.players.Role;
import laxstats.api.teams.TeamGender;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonQueryRepository extends PagingAndSortingRepository<PersonEntry, String>,
   JpaSpecificationExecutor<PersonEntry>
{

   List<PersonEntry> findByLastName(String lastName);

   @Query("select p from PersonEntry p " + "left join p.addresses a left join p.contacts c "
      + "where(p.firstName like %?1% and p.lastName like %?2%) "
      + "order by p.lastName, p.firstName, a.region, a.city")
   List<PersonEntry> searchPeople(String firstName, String LastName);

   @Query(value = "select exists(select 1 from AddressEntry ae where ?1 is not null "
      + "and ae.id = cast(?1 as text))", nativeQuery = true)
   boolean checkAddressExists(String addressId);

   @Query("select count(*) from AddressEntry ae " + "where ae.addressType = ?1 "
      + "and ?2 is not null and upper(ae.address1) = upper(?2) "
      + "and upper(ae.city) = upper(?3) and ae.region = ?4 " + "and ae.person.id = ?5")
   int uniqueAddress(AddressType type, String address1, String city, Region region, String personId);

   @Query("select count(*) from AddressEntry ae " + "where ae.addressType = ?1 "
      + "and ?2 is not null and upper(ae.address1) = upper(?2) "
      + "and upper(ae.city) = upper(?3) and ae.region = ?4 "
      + "and ae.person.id = ?5 and ae.id <> ?6")
   int updateAddress(AddressType type, String address1, String city, Region region, String personId,
      String addressId);

   @Query(value = "select exists(select 1 from contacts ce where ?1 is not null "
      + "and ce.id = cast(?1 as text))", nativeQuery = true)
   boolean checkContactExists(String contactId);

   @Query("select count(*) from ContactEntry ce "
      + "where ce.method = ?1 and upper(ce.value) = upper(?2) " + "and ce.person.id = ?3")
   int uniqueContact(ContactMethod method, String value, String personId);

   @Query("select count(*) from ContactEntry ce "
      + "where ce.method = ?1 and upper(ce.value) = upper(?2) "
      + "and ce.person.id = ?3 and ce.id <> ?4")
   int updateContact(ContactMethod method, String value, String personId, String contactId);

   @Query(value = "select t.gender from people p " + "left join players pl on pl.person _id = p.id "
      + "left join team_seasons ts on ts.id = pl.team_season_id "
      + "left join teams t on t.id = ts.team "
      + "where ?1 is not null and p.id = ?1 and pl.role = ?2", nativeQuery = true)
   TeamGender getTeamGender(String personId, Role role);
}
