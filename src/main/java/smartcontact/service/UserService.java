package smartcontact.service;

import smartcontact.dto.ChangePasswordDto;
import smartcontact.dto.UserSignInDto;
import smartcontact.dto.UserSignUpDto;
import smartcontact.entities.UserSignUp;
import smartcontact.util.Response;

import java.util.List;

public interface UserService {

    UserSignUp saveUser(UserSignUpDto userDto);

    void deleteUser(long id);

    List<UserSignUp> getAllUsers();

    UserSignUp updateUser(long userId, UserSignUpDto userDto);

    UserSignUp getUserById(long userId);

    UserSignUp userSignIn(UserSignInDto userDto);

    Response changePassword(ChangePasswordDto changePasswordDto);
}
