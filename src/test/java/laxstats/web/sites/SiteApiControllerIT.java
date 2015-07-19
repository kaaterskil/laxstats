package laxstats.web.sites;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.AddressEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SiteApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/sites/";

   @Autowired
   private SiteQueryRepository repository;

   private final List<SiteEntry> sites = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      repository.deleteAll();

      final String addressId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final AddressEntry address = new AddressEntry();
      address.setAddress1("390 Lincoln Road");
      address.setAddressType(AddressType.SITE);
      address.setCity("Sudbury");
      address.setId(addressId);
      address.setRegion(Region.MA);
      address.setPostalCode("01776");

      final SiteEntry entity = new SiteEntry();
      entity.setAddress(address);
      entity.setCreatedAt(LocalDateTime.now());
      entity.setId(new SiteId().toString());
      entity.setModifiedAt(LocalDateTime.now());
      entity.setName("Lincoln Sudbury Regional High School");
      entity.setStyle(SiteStyle.COMPETITION);
      entity.setSurface(Surface.GRASS);

      repository.save(entity);
      sites.add(entity);
   }

   /*---------- Public method tests ----------*/

   @Test
   public void siteNotFound() throws Exception {
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readSite() throws Exception {
      final SiteEntry entity = sites.get(0);
      final String getUrl = BASE_REQUEST_URL + entity.getId();

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(entity.getId())))
         .andExpect(jsonPath("$.name", is(entity.getName())));
   }

   @Test
   public void readSites() throws Exception {
      final SiteEntry entity = sites.get(0);

      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(entity.getId())))
         .andExpect(jsonPath("$[0].name", is(entity.getName())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createSite() throws Exception {
      final SiteResource resource = TestUtils.newSiteResource();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.name", is(resource.getName())));
   }

   @Test
   public void createSiteWithValidationError() throws Exception {
      final SiteResource resource = TestUtils.newSiteResource();
      resource.setRegion(null);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updateSite() throws Exception {
      final String newName = "WHS Memorial Field";

      final SiteResource resource = transform(sites.get(0));
      resource.setName(newName);

      final String putUrl = BASE_REQUEST_URL + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(resource.getId())))
         .andExpect(jsonPath("$.name", is(newName)));
   }

   @Test
   public void updateSiteWithValidationError() throws Exception {
      final SiteResource resource = transform(sites.get(0));
      resource.setName(null);

      final String putUrl = BASE_REQUEST_URL + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteSite() throws Exception {
      final SiteEntry entity = sites.get(0);
      final String deleteUrl = BASE_REQUEST_URL + entity.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType))
         .andExpect(status().isOk());
   }

   /*---------- Utilities ----------*/

   private SiteResource transform(SiteEntry entity) {
      final SiteResourceImpl resource =
         new SiteResourceImpl(entity.getId(), entity.getName(), entity.getStyle(),
            entity.getSurface(), entity.getDirections());

      final AddressEntry address = entity.getAddress();
      if (address != null) {
         resource.setAddress1(address.getAddress1());
         resource.setAddress2(address.getAddress2());
         resource.setCity(address.getCity());
         resource.setPostalCode(address.getPostalCode());
         resource.setRegion(address.getRegion());
      }

      return resource;
   }
}
