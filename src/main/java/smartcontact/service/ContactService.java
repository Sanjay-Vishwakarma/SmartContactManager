package smartcontact.service;

import smartcontact.dto.ContactDto;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    ContactDto createContact(ContactDto contactDto);

    Optional<ContactDto> getContactById(int id);

    List<ContactDto> getAllContacts();

    ContactDto updateContact(int id, ContactDto contactDto);

    boolean deleteContact(int id);

    List<ContactDto> getAllContactsByUid(int uid);
}
