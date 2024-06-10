package smartcontact.service;

import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.UserSignUp;

import java.util.List;

public interface UserService {

    UserSignUp saveUser(UserSignUpDto userDto);

    void deleteUser(long id);

    List<UserSignUp> getAllUsers();

    UserSignUp updateUser(long userId, UserSignUpDto userDto);

    UserSignUp getUserById(long userId);

    UserSignUp userSignIn(UserSignInDto userDto);
}
