package laxstats.query.sites;

import laxstats.api.Region;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sites", path = "sites")
public interface SiteQueryRepository extends PagingAndSortingRepository<SiteEntry, String> {

   @Query("select count(*) from SiteEntry se "
      + "where se.address is not null and upper(se.name) = upper(cast(?1 as text)) "
      + "and upper(se.address.city) = upper(cast(?2 as text)) and se.address.region = ?3")
   int uniqueName(String name, String city, Region region);

   @Query("select count(*) from SiteEntry se "
      + "where se.address is not null and upper(se.name) = upper(cast(?1 as text)) "
      + "and upper(se.address.city) = upper(cast(?2 as text)) "
      + "and se.address.region = ?3 and se.id <> ?4")
   int updateName(String name, String city, Region region, String siteId);

   @Query("select count(*) from TeamEntry te where te.homeSite.id = ?1")
   int countTeams(String siteId);

   @Query("select count(*) from GameEntry ge where ge.site.id = ?1")
   int countGames(String siteId);
}
