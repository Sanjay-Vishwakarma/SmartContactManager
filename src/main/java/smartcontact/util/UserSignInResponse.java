package smartcontact.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInResponse {

    private String message;
    private Long uId;

    public UserSignInResponse(String message, Long uId) {
        this.message = message;
        this.uId = uId;
    }
}
