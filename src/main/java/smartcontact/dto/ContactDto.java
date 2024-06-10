package smartcontact.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactDto {
    private int id=0;
    private String uid; // Ensure this matches the userId type in Contact entity
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String description;
}
