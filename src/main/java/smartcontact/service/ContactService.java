package smartcontact.service;

import smartcontact.dto.ContactDto;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    ContactDto createContact(ContactDto contactDto);

    Optional<ContactDto> getContactById(long id);

    List<ContactDto> getAllContacts();

    ContactDto updateContact(long id, ContactDto contactDto);

    boolean deleteContact(long id);
}
