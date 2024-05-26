package smartcontact.serviceImpl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartcontact.dto.ContactDto;
import smartcontact.entities.Contact;
import smartcontact.entities.UserSignUp;
import smartcontact.repository.ContactRepository;
import smartcontact.repository.UserRepository;
import smartcontact.service.ContactService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    // Convert ContactDto to Contact entity
    private Contact dtoToEntity(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setLastName(contactDto.getLastName());
        contact.setPhone(contactDto.getPhone());
        contact.setDescription(contactDto.getDescription());
        return contact;
    }

    // Convert Contact entity to ContactDto
    private ContactDto entityToDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setName(contact.getName());
        contactDto.setLastName(contact.getLastName());
        contactDto.setPhone(contact.getPhone());
        contactDto.setDescription(contact.getDescription());
        return contactDto;
    }

    // Create a new contact
    @Transactional
    public ContactDto createContact(ContactDto contactDto) {
        Contact savedContact = null;
        try {
            UserSignUp userSignUp = userRepository.findByUsername(contactDto.getUsername());
            Contact contact = dtoToEntity(contactDto);
            contact.setUserId(userSignUp.getId());
            savedContact = contactRepository.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityToDto(savedContact);
    }

    // Get a contact by ID
    public Optional<ContactDto> getContactById(long id) {
        return contactRepository.findById(id).map(this::entityToDto);
    }

    // Get all contacts
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    // Update a contact
    @Transactional
    public ContactDto updateContact(long id, ContactDto contactDto) {
        Contact updatedContact = null;
        try {
            Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
            contact.setName(contactDto.getName());
            contact.setLastName(contactDto.getLastName());
            contact.setPhone(contactDto.getPhone());
            contact.setDescription(contactDto.getDescription());
            contact.setUpdatedAt(LocalDate.now());
            updatedContact = contactRepository.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityToDto(updatedContact);
    }

    // Delete a contact
    public boolean deleteContact(long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        contactRepository.delete(contact);
        return false;
    }
}
