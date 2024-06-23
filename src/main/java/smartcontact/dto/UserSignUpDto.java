package smartcontact.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {

    private long id;
    private String name;
    private String email;
    private String desc;
    private String username;
    private String password;

}
