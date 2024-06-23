package smartcontact.validatorImpl;

import org.springframework.stereotype.Component;
import smartcontact.dto.UserSignUpDto;
import smartcontact.exception.ValidationException;
import smartcontact.validator.Validator;

@Component
public class ValidatorImpl implements Validator {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{8,}$"; // Regex for 8 alphanumeric characters

    @Override
    public void userValidate(UserSignUpDto userDto) throws ValidationException {
        if (userDto.getName() == null || userDto.getName().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new ValidationException("Username cannot be empty");
        }
        if (!userDto.getUsername().matches(USERNAME_REGEX)) {
            throw new ValidationException("Username must be exactly 8 alphanumeric characters");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }
        // Add more validation logic as required
    }
}
