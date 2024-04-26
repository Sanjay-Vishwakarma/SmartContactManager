package smartcontact.serviceImpl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartcontact.dto.ContactDto;
import smartcontact.dto.UserDto;
import smartcontact.entities.Contact;
import smartcontact.entities.User;
import smartcontact.exception.InvalidRequestException;
import smartcontact.exception.UserNotFoundException;
import smartcontact.repository.ContactRepository;
import smartcontact.repository.UserRepository;
import smartcontact.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ContactRepository contactRepository;




    @Transactional
    public User saveUser(UserDto userDto) {
        // Check if email already exists for the user
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidRequestException("Duplicate Email: " + userDto.getEmail());
        }

        try {
            // Map UserDto to User entity
            User userToSave = modelMapper.map(userDto, User.class);
            userToSave.setCreatedAt(LocalDate.now());

            // Validate and map ContactDtos to Contact entities
            List<Contact> contactsToSave = new ArrayList<>();
            for (ContactDto contactDto : userDto.getContacts()) {
                if (userRepository.existsByEmail(contactDto.getEmail())) {
                    throw new InvalidRequestException("Duplicate Contact Email: " + contactDto.getEmail());
                }
                Contact contact = modelMapper.map(contactDto, Contact.class);
                contact.setCreatedAt(LocalDate.now());
                contact.setUser(userToSave); // Set the user for each contact
                contactsToSave.add(contact);
            }

            // Set the contacts for the user
            userToSave.setContacts(contactsToSave);

            // Save the user entity (including associated contacts)
            userRepository.save(userToSave);

            return userToSave;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to save user", e);
        }
    }



    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    @Transactional
    public User updateUser(int userId, UserDto userDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                // Update user fields from UserDto
                existingUser.setName(userDto.getName());
                existingUser.setEmail(userDto.getEmail());
                existingUser.setPassword(userDto.getPassword());
                existingUser.setRole(userDto.getRole());
                existingUser.setEnabled(userDto.isEnabled());
                existingUser.setImageUrl(userDto.getImageUrl());
                existingUser.setAbout(userDto.getAbout());
                existingUser.setUpdatedAt(LocalDate.now());

                // Update or save contacts
                List<ContactDto> contactDtos = userDto.getContacts();
                for (ContactDto contactDto : contactDtos) {
                    Optional<Contact> optionalContact = contactRepository.findById(contactDto.getId());
                    if (optionalContact.isPresent()) {
                        Contact existingContact = optionalContact.get();
                        // Update existing contact fields from ContactDto
                        existingContact.setName(contactDto.getName());
                        existingContact.setSecondName(contactDto.getSecondName());
                        existingContact.setWork(contactDto.getWork());
                        existingContact.setEmail(contactDto.getEmail());
                        existingContact.setPhone(contactDto.getPhone());
                        existingContact.setImage(contactDto.getImage());
                        existingContact.setDescription(contactDto.getDescription());
                        existingContact.setUpdatedAt(LocalDate.now());
                    } else {
                        System.out.printf("Contact not found with id: %d\n", contactDto.getId());
                    }
                }
                // Save updated user (including contacts)
                return userRepository.save(existingUser);
            } else {
                throw new UserNotFoundException("User not found with id: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public User getUserById(int userId) { // 1st way
        User user = userRepository.findById(userId).orElse(null); // Find user or return null
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return user;
    }

//    @Override
//    public User getUserById(int userId) { // 2nd way
//        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//    }


}
