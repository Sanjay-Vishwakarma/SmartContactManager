package smartcontact.serviceImpl;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartcontact.dto.ContactDto;
import smartcontact.entities.Contact;
import smartcontact.entities.UserSignUp;
import smartcontact.repository.ContactRepository;
import smartcontact.repository.UserRepository;
import smartcontact.service.ContactService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    // Convert ContactDto to Contact entity

    // Convert Contact entity to ContactDto
    private ContactDto entityToDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setFirstName(contact.getFirstName());
        contactDto.setLastName(contact.getLastName());
        contactDto.setEmail(contact.getEmail());
        contactDto.setPhone(contact.getPhone());
        contactDto.setDescription(contact.getDescription());
        return contactDto;
    }

    // Create a new contact
    @Transactional
    public ContactDto createContact(ContactDto contactDto) {
        Contact savedContact = null;
        System.out.println("Received contactDto with uId: " + contactDto.getUid());
        try {
            Optional<UserSignUp> user = userRepository.findById(Long.valueOf(contactDto.getUid()));
            if (user.isPresent()) {
                UserSignUp existUser = user.get();
                System.out.println("Found user with uId: " + existUser.getId());

                Optional<Contact> byEmail = contactRepository.findByEmail(contactDto.getEmail());
                if (byEmail.isPresent()) {
                    System.out.println("Contact with email " + contactDto.getEmail() + " already exists.");
                } else {
                    Contact contact = dtoToEntity(contactDto);
                    contact.setUserId(String.valueOf(existUser.getId()));
                    savedContact = contactRepository.save(contact);
                    System.out.println("Saved new contact with email " + contactDto.getEmail());
                }
            } else {
                System.out.println("No user found with uId: " + contactDto.getUid()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityToDto(savedContact);
    }


    // Convert ContactDto to Contact entity
    private Contact dtoToEntity(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setCreatedAt(LocalDate.now());
        contact.setDescription(contactDto.getDescription());
        return contact;
    }


    // Get a contact by ID
    public Optional<ContactDto> getContactById(int id) {
        return contactRepository.findById(id).map(this::entityToDto);
    }

    // Get all contacts
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    // Update a contact
    @Transactional
    public ContactDto updateContact(int id, ContactDto contactDto) {
        Contact updatedContact = null;
        try {
            Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
            contact.setFirstName(contactDto.getFirstName());
            contact.setLastName(contactDto.getLastName());
            contact.setPhone(contactDto.getPhone());
            contact.setEmail(contactDto.getEmail());
            contact.setDescription(contactDto.getDescription());
            contact.setUpdatedAt(LocalDate.now());
            updatedContact = contactRepository.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityToDto(updatedContact);
    }

    // Delete a contact
    public boolean deleteContact(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        contactRepository.delete(contact);
        return false;
    }

    @Override
    public List<ContactDto> getAllContactsByUid(int uid) {
        List<ContactDto> contactDtos = null;
        try {
            List<Contact> contacts = contactRepository.findAllContactByUid(uid);
            System.out.println("contacts = " + contacts);
            contactDtos = contacts.stream()
                    .map(contact -> modelMapper.map(contact, ContactDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // It's better to log this properly in a real application
        }
        return contactDtos;
    }
}
