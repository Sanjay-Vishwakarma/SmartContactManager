package smartcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcontact.entities.UserSignUp;

public interface UserRepository extends JpaRepository<UserSignUp, Long> {

    boolean existsByEmail(String email);

//    UserSignUp findByUsername(String username);

    boolean existsByUsername(String username);

    UserSignUp findByUsername(String password);

//    UserSignUp findByPasswordAndUsername(String password, String decrypt);
}
