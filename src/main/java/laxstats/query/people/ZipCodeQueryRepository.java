package laxstats.query.people;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "zip_codes")
public interface ZipCodeQueryRepository extends
   CrudRepository<ZipCode, String> {

   /**
    * Returns a list of {@code ZipCode}s corresponding to the given ZIP code, or an empty list if
    * information is not available for the given ZIP code.
    *
    * @param zipCode
    * @return
    */
      List<ZipCode> findByZipCode(String zipCode);
}
