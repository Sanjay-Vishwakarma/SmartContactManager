package smartcontact.serviceImpl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartcontact.dto.ContactDto;
import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.Contact;
import smartcontact.entities.UserSignUp;
import smartcontact.exception.InvalidRequestException;
import smartcontact.exception.UserNotFoundException;
import smartcontact.repository.ContactRepository;
import smartcontact.repository.UserRepository;
import smartcontact.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserRepository userRepository;

    @Transactional
    public UserSignUp saveUser(UserSignUpDto userDto) {
        // Check if email already exists for the user
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidRequestException("Duplicate Email: " + userDto.getEmail());
        }
        if (userRepository.existsByEmail(userDto.getUsername())) {
            throw new InvalidRequestException("Duplicate username: " + userDto.getUsername());
        }
        try {
            UserSignUp user = modelMapper.map(userDto, UserSignUp.class);
            user.setCreatedAt(LocalDate.now());
            user.setRole("User");
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to save user", e);
        }
    }


    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserSignUp> getAllUsers() {
        List<UserSignUp> userList = userRepository.findAll();
        return userList;
    }

    @Override
    @Transactional
    public UserSignUp updateUser(long userId, UserSignUpDto userDto) {
        try {
            Optional<UserSignUp> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                UserSignUp existingUser = optionalUser.get();
                existingUser.setName(userDto.getName());
                existingUser.setUsername(userDto.getUsername());
                existingUser.setEmail(userDto.getEmail());
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
    public UserSignUp getUserById(long userId) { // 1st way
        UserSignUp user = userRepository.findById(userId).orElse(null); // Find user or return null
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return user;
    }

    @Override
    public UserSignUp userSignIn(UserSignInDto userDto) {
        UserSignUp userDetails = null;
        try {
            userDetails = userRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
            System.out.println("userDetails = " + userDetails);
            if (userDetails != null && userDto.getPassword().equals(userDetails.getPassword())) {
                return userDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }
}
