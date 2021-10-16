package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserViewMapperTest {
  private final static String NAME = "fulano";
  private final static String PASSWORD = "123";
  private final static String EMAIL = "fulano@mail.com";

  private final UserViewMapper mapper = new UserViewMapper();

  @Test
  void shouldConvertUserToUserView() {
    var user = new User(NAME, EMAIL, PASSWORD);

    final UserView userView = mapper.map(user);

    assertEquals(NAME, userView.getName());
    assertEquals(EMAIL, userView.getEmail());
  }
}