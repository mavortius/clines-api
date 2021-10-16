package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserFormMapperTest {
  private static final String NAME = "fulano";
  private static final String EMAIL = "fulano@mail.com";
  private static final String PASSWORD = "123";

  private final UserFormMapper mapper = new UserFormMapper();

  @Test
  void shouldConvertUserFormToUser() {
    UserForm userForm = new UserForm(NAME, EMAIL, PASSWORD);

    final User user = mapper.map(userForm);

    assertEquals(NAME, user.getName());
    assertEquals(EMAIL, user.getEmail());
    assertEquals(PASSWORD, user.getPassword());
  }
}