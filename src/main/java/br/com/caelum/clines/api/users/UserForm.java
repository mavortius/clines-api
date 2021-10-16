package br.com.caelum.clines.api.users;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserForm {

  private Long id;

  @NotNull
  @Size(min = 3, max = 50)
  private String name;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size(min = 3)
  private String password;

  public UserForm(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }
}
