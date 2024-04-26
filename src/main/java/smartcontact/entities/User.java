    package smartcontact.entities;


    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Data;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Data
    @Table(name = "USER")
    public class User {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private int id;
        private String name;
        @Column(unique=true)
        private String email;
        private String password;
        private String role;
        private boolean enabled = true;
        private String imageUrl;
        @Column(length = 300)
        private String about;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "user")
        @JsonManagedReference
        private List<Contact> contacts =new ArrayList<>();


    }
