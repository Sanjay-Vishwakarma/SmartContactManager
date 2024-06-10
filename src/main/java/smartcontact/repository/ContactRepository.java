package smartcontact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartcontact.dto.ContactDto;
import smartcontact.entities.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

    Optional<Contact> findByEmail(String email);

    //List<Contact> findAllContactByUid(int uid);

    @Query("SELECT c FROM Contact c WHERE c.userId = :uid")
    List<Contact> findAllContactByUid(@Param("uid") int uid);
}
