package smartcontact.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private  int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean enabled = true;
    private String imageUrl;
    private String about;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private List<ContactDto> contacts =new ArrayList<>();

}
