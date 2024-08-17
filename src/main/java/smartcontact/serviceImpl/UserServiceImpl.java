package smartcontact.serviceImpl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartcontact.dto.ChangePasswordDto;
import smartcontact.dto.ContactDto;
import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.Contact;
import smartcontact.entities.UserSignUp;
import smartcontact.exception.InvalidRequestException;
import smartcontact.exception.UserNotFoundException;
import smartcontact.repository.ContactRepository;
import smartcontact.repository.UserRepository;
import smartcontact.security.AESSecurity;
import smartcontact.service.UserService;
import smartcontact.util.ConstantMessage;
import smartcontact.util.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserRepository userRepository;

    @Value("${secretKey}")
    private String secretKey;

    @Transactional
    public UserSignUp saveUser(UserSignUpDto userDto) {
        // Check if email already exists for the user
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidRequestException("Already taken Email: " + userDto.getEmail());
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new InvalidRequestException("Already taken username: " + userDto.getUsername());
        }
        try {
            UserSignUp user = modelMapper.map(userDto, UserSignUp.class);
            user.setPassword(AESSecurity.encrypt(userDto.getPassword(), secretKey, userDto.getUsername()));
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(ConstantMessage.USER);
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
                existingUser.setUpdatedAt(LocalDateTime.now());
                return userRepository.save(existingUser);
            } else {
                throw new UserNotFoundException("User not found with id: " + userId);
            }
        } catch (Exception e) {
           logger.info("Failed to update user", e.getMessage());
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
            userDetails = userRepository.findByUsername(userDto.getUsername());
            String paassword = AESSecurity.decrypt(userDetails.getPassword(), secretKey, userDto.getUsername());
            if (userDetails != null && userDto.getPassword().equals(paassword)) {
                return userDetails;
            } else {
                logger.info("inside else...");
                return null;
            }
        } catch (Exception e) {
            logger.info("something went wrong ====="+UserServiceImpl.class.getName() + e.getMessage());
        }
        return userDetails;
    }

    @Override
    public Response changePassword(ChangePasswordDto changePasswordDto) {
        Response response = new Response();
        try {
            if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
                response.setMsg("Passwords do not match");
                return response;
            }
            Optional<UserSignUp> byId = userRepository.findById(Long.valueOf(changePasswordDto.getUserId()));
            if (byId.isPresent()) {
                UserSignUp userSignUp1 = byId.get();
                String userName = userSignUp1.getUsername();
                String confirmPassword = changePasswordDto.getConfirmPassword();
                userSignUp1.setPassword(AESSecurity.encrypt(confirmPassword, secretKey, userName));
                userRepository.save(userSignUp1);
                response.setMsg("Password changed successfully .... !");
                response.setData("User successfully changed");
            }
        } catch (Exception e) {
            logger.info("something went issue while changing password ====="+UserServiceImpl.class.getName() + e.getMessage());
        }
        return response;
    }
}
