package smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcontact.dto.ContactDto;
import smartcontact.service.ContactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact")
@CrossOrigin("*")
public class ContactController {


    @Autowired
    private ContactService contactService;

    // Create a new contact
    @PostMapping("/addContact")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        ContactDto createdContact = contactService.createContact(contactDto);
        return ResponseEntity.ok(createdContact);
    }

    // Get a contact by ID
    @GetMapping("getContact/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable int id) {
        Optional<ContactDto> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all contacts
    @GetMapping("/getAllContacts")
    public List<ContactDto> getAllContacts() {
        return contactService.getAllContacts();
    }

    // Update a contact
    @PutMapping("/update/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable long id, @RequestBody ContactDto contactDto) {
        ContactDto updatedContact = contactService.updateContact(id, contactDto);
        return ResponseEntity.ok(updatedContact);
    }

    // Delete a contact
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

}
