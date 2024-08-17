package smartcontact.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcontact.dto.ChangePasswordDto;
import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.UserSignUp;
import smartcontact.service.UserService;
import smartcontact.util.Response;
import smartcontact.util.UserSignInResponse;
import smartcontact.validatorImpl.ValidatorImpl;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorImpl validator;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/addUser")
    public ResponseEntity<String> saveUser(@RequestBody UserSignUpDto userDto) {
        validator.userValidate(userDto);
        userService.saveUser(userDto);
        return new ResponseEntity<>("Added User Successfully...!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted User Successfully...!", HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserSignUp>> getAllUsers() {
        List<UserSignUp> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserSignUp> getUserById(@PathVariable int userId) {
        UserSignUp user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody UserSignUpDto userDto) {
        try {
            userService.updateUser(userId, userDto);
            return new ResponseEntity<>("Updated Successfully ...!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // user sign in
    @PostMapping("/sign")
    public ResponseEntity<UserSignInResponse> userSignIn(@RequestBody UserSignInDto userDto) {
        UserSignUp userSignUp = userService.userSignIn(userDto);

        if (userSignUp != null) {
            UserSignInResponse response = new UserSignInResponse("User Sign In Successfully...!", userSignUp.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        UserSignInResponse response = new UserSignInResponse("User Sign In Failed...!", "Inavlid credential... ");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        Response response = null;
        String userId = changePasswordDto.getUserId();
        if (userId != null) {
            response = userService.changePassword(changePasswordDto);
            response.setMsg("Changed Password Successfully...!");
        }else {
            response.setMsg("userId  not found or not be null");
            logger.info("userId not found ....");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
