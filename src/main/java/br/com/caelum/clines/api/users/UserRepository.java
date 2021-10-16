package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findAll();
}
