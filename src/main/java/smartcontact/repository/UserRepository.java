package smartcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartcontact.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
}
