package smartcontact.util;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponse {

    private String message;
    private Long uId;
    private String description;

    public UserSignInResponse(String s, long id) {
        this.message = s;
        this.uId = id;
    }

    public UserSignInResponse(String s, String s1) {
        this.message=s;
        this.description=s1;
    }
}
