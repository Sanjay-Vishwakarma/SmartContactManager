package smartcontact.service;

import smartcontact.dto.UserDto;
import smartcontact.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(UserDto userDto);

    void deleteUser(int id);

    List<User> getAllUsers();

    User updateUser(int userId, UserDto userDto);

    User getUserById(int userId);
}
