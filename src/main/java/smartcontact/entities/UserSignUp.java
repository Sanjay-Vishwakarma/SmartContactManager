    package smartcontact.entities;


    import jakarta.persistence.*;
    import lombok.Data;
    import java.time.LocalDate;

    @Entity
    @Data
    @Table(name = "USER")
    public class UserSignUp {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long id;

        private String name;

        @Column(unique=true)
        private String email;

        private String username;

        private String password;

        private String role; //  default

        private boolean enabled = true; // active or inactive

//       private String imageUrl;

        private LocalDate createdAt;

        private LocalDate updatedAt;


    }
