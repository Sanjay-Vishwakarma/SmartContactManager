package smartcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcontact.entities.UserSignUp;

public interface UserRepository extends JpaRepository<UserSignUp, Long> {

    boolean existsByEmail(String email);

    UserSignUp findByUsernameAndPassword(String username, String password);

    UserSignUp findByUsername(String username);
}
