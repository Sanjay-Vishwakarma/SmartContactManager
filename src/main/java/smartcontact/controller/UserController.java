package smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.UserSignUp;
import smartcontact.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> saveUser(@RequestBody UserSignUpDto userDto) {
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
    public ResponseEntity<String> userSignIn(@RequestBody UserSignInDto userDto) {
        boolean b = userService.userSignIn(userDto);
        if (b) {
            return new ResponseEntity<>("User Sign In Successfully...!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Sign In Failed...!", HttpStatus.BAD_REQUEST);
    }

}
