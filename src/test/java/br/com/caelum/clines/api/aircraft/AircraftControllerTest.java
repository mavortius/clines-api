package br.com.caelum.clines.api.aircraft;

import br.com.caelum.clines.shared.domain.Aircraft;
import br.com.caelum.clines.shared.domain.AircraftModel;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
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
class AircraftControllerTest {
  private static final String AIRCRAFT_RESOURCE_URI = "/aircraft";
  private static final String AIRCRAFT_CODE = "ASDF1234";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestEntityManager entityManager;

  private AircraftModel BOEING;

  @BeforeEach
  void setup() {
    BOEING = new AircraftModel("Boeing 737 800");
    entityManager.persist(BOEING);
  }

  @Test
  void shouldReturn404WhenNotExistAircraftByCode() throws Exception {
    mockMvc.perform(get(AIRCRAFT_RESOURCE_URI + "/ASDF1234"))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnAnAircraftByCode() throws Exception {
    var aircraft = new Aircraft(AIRCRAFT_CODE, BOEING);

    entityManager.persist(aircraft);

    mockMvc.perform(get(AIRCRAFT_RESOURCE_URI + "/" + AIRCRAFT_CODE))
            .andExpect(status().isOk())
            .andDo(log())
            .andExpect(jsonPath("$.code", equalTo("ASDF1234")))
            .andExpect(jsonPath("$.model.id", equalTo(BOEING.getId().intValue())))
            .andExpect(jsonPath("$.model.description", equalTo(BOEING.getDescription())));
  }

  @Test
  void shouldCreateAnAircraftAndReturn201() throws Exception {
    var form = new AircraftForm(AIRCRAFT_CODE, BOEING.getId());
    final String json = new Gson().toJson(form);
    mockMvc.perform(post(AIRCRAFT_RESOURCE_URI + "/").content(json).contentType(MediaType.APPLICATION_JSON))
            .andDo(log())
            .andExpect(status().isCreated());
  }
}