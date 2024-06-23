package smartcontact.validator;

import smartcontact.dto.UserSignUpDto;
import smartcontact.exception.ValidationException;

public interface Validator {

    void userValidate(UserSignUpDto userSignUpDto) throws ValidationException;
}
