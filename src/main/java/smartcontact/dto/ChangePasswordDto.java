package smartcontact.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordDto {

    private String userId;

    private String newPassword;

    private String confirmPassword;
}
