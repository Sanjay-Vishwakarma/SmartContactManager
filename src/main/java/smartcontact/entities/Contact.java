package smartcontact.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;

    private String lastName;

    private String phone;

    //private String image;

    @Column(length = 3000)
    private String description;

    private long userId;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
