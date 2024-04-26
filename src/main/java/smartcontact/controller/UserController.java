package smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcontact.dto.UserDto;
import smartcontact.entities.User;
import smartcontact.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return new ResponseEntity<>("Added User Successfully...!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted User Successfully...!", HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
        return new ResponseEntity<>("Updated Successfully ...!", HttpStatus.OK);
    }

}
