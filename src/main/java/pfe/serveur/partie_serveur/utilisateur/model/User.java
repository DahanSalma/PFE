package pfe.serveur.partie_serveur.utilisateur.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;



    public User(String email, String password, String role, String firstname,String lastname ) {

        this.email = email;
        this.password = password;
        this.role = role;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public User() {

    }


}