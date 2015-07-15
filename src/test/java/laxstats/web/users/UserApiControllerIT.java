package laxstats.web.users;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import laxstats.TestUtils;
import laxstats.api.users.UserId;
import laxstats.api.users.UserRole;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = BASE_URL + "/api/users/";

   @Autowired
   private UserQueryRepository repository;

   @Before
   public void setup() {
      createSecureContext();
   }

   @Test
   public void getUsers() throws Exception {
      mockMvc.perform(get(BASE_REQUEST_URL).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)));
   }

   @Test
   public void getUsersNotAuthorized() throws Exception {
      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isForbidden());
   }

   @Test
   public void getUserNotFound() throws Exception {
      final String id = new UserId().toString();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isNotFound());
   }

   @Test
   public void getUserNotAuthorized() throws Exception {
      final String id = new UserId().toString();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isForbidden());
   }

   @Test
   public void getUser() throws Exception {
      final UserEntry user = repository.findByEmail(System.getenv("authentication.username"));
      final String id = user.getId();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.lastName", is(user.getLastName())));
   }

   @Test
   public void createUser() throws Exception {
      final UserResource resource = getMockUser();
      final String email = resource.getEmail();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.lastName", is("Doe")));

      final UserEntry user = repository.findByEmail(email);
      repository.delete(user);
   }

   @Test
   public void createUserWithValidationError() throws Exception {
      final UserResource resource = getMockUser();
      resource.setPassword(null);

      final String email = resource.getEmail();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest());

      final UserEntry user = repository.findByEmail(email);
      if (user != null) {
         repository.delete(user);
      }
   }

   @Test
   public void updateUser() throws Exception {
      final String id = new UserId().toString();
      final UserEntry user = getMockUserEntry();
      user.setId(id);
      repository.save(user);

      final UserResource resource =
         new UserResourceImpl(user.getId(), null, user.getEmail(), user.getPassword(),
            user.isEnabled(), user.getFirstName(), user.getLastName(), user.getRole());
      resource.setRole(UserRole.MANAGER);
      final String putUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(put(putUrl).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.role", is(UserRole.MANAGER.name())));

      repository.delete(id);
   }

   @Test
   public void updateUserWithValidationError() throws Exception {
      final String id = new UserId().toString();
      final UserEntry user = getMockUserEntry();
      user.setId(id);
      repository.save(user);

      final UserResource resource =
         new UserResourceImpl(user.getId(), null, user.getEmail(), user.getPassword(),
            user.isEnabled(), user.getFirstName(), user.getLastName(), user.getRole());
      resource.setEmail(null);
      final String putUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(put(putUrl).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));

      repository.delete(id);
   }

   @Test
   public void deleteUser() throws Exception {
      final String id = new UserId().toString();
      final UserEntry user = getMockUserEntry();
      user.setId(id);
      repository.save(user);

      final String deleteUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isOk());

      repository.delete(id);
   }

   @Test
   public void deleteUserWithInvalidId() throws Exception {
      final String id = new UserId().toString();
      final UserEntry user = getMockUserEntry();
      user.setId(id);
      repository.save(user);

      final String deleteUrl = BASE_REQUEST_URL + new UserId().toString();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isNotFound());

      repository.delete(id);
   }


   /*---------- Utilities ----------*/

   private UserResource getMockUser() {
      final UserResource resource = new UserResourceImpl();
      resource.setEmail("john@example.com");
      resource.setEnabled(true);
      resource.setFirstName("John");
      resource.setLastName("Doe");
      resource.setPassword("password");
      resource.setRole(UserRole.COACH);
      return resource;

   }

   private UserEntry getMockUserEntry() {
      final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      final String encodedPassword = encoder.encode("password");

      final UserEntry entity = new UserEntry();
      entity.setEmail("john@example.com");
      entity.setEnabled(true);
      entity.setFirstName("John");
      entity.setLastName("Doe");
      entity.setEncodedPassword(encodedPassword);
      entity.setRole(UserRole.COACH);
      return entity;
   }
}
