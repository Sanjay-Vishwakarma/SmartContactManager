package smartcontact.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        //Long uId = (Long) session.getAttribute("uId");
        System.out.println("contactDto = " + contactDto);
        String uId = String.valueOf(contactDto.getUid());
        System.out.println("uId = " + uId);
        if (uId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        contactDto.setUid(uId);
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
    @GetMapping("/getAllContacts/{uid}")
    public List<ContactDto> getAllContacts(@PathVariable int uid) {
        System.out.println("uid = " + uid);
        List<ContactDto> allContacts = null;
        try {
            allContacts = contactService.getAllContactsByUid(uid);
            System.out.println("allContacts = " + allContacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    // Update a contact
    @PutMapping("/updateContact/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable int id, @RequestBody ContactDto contactDto) {
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
