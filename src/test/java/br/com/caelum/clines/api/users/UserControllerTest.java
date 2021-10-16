package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = {"DB_NAME=clines_test", "spring.jpa.hibernate.ddlAuto:create-drop"})
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class UserControllerTest {
  private static final String USERS_RESOURCE_URI = "/users";
  private static final String USER_NAME = "fulano";
  private static final String USER_EMAIL = "fulano@mail.com";
  private static final String USER_PASSWORD = "123";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void shouldReturn404WhenNotExistUserById() throws Exception {
    mockMvc.perform(get(USERS_RESOURCE_URI + "/999")).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnAnUserById() throws Exception {
    var user = new User(USER_NAME, USER_EMAIL, USER_PASSWORD);
    entityManager.persist(user);

    mockMvc.perform(get(USERS_RESOURCE_URI + "/1")).andExpect(status().isOk())
            .andDo(log())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(jsonPath("$.name", equalTo(user.getName())))
            .andExpect(jsonPath("$.email", equalTo(user.getEmail())));
  }

  @Test
  void shouldCreateAnUserAndReturn201() throws Exception {
    var form = new UserForm(USER_NAME, USER_EMAIL, USER_PASSWORD);
    final String json = new Gson().toJson(form);

    mockMvc.perform(post(USERS_RESOURCE_URI).contentType(MediaType.APPLICATION_JSON).content(json))
            .andDo(log())
            .andExpect(status().isCreated());
  }
}