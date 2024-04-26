package smartcontact.dto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ContactDto {

    private int id;
    private String name;
    private String secondName;
    private String work;
    private String email;
    private String phone;
    private String image;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
