package smartcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartcontact.entities.Contact;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

}
