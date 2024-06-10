package smartcontact.entities;

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
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Column(length = 3000)
    private String description;

    private String userId; // Ensure this matches the uId type in ContactDto

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
