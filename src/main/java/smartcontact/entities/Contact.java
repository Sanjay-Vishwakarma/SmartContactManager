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
            private int id;
            private String name;
            private String secondName;
            private String work;
            private String email;
            private String phone;
            private String image;
            @Column(length = 3000)
            private String description;
            private LocalDate createdAt;
            private LocalDate updatedAt;

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "user_id")
            @JsonIgnore
            private User user;
        }
