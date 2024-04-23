package pfe.serveur.partie_serveur.reservation.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pfe.serveur.partie_serveur.utilisateur.model.User;

@Getter
@Setter
@Component
@Entity
@Table(name = "resrver", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class ReserverForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String adresse;
    private int dateHeure;
    private int nombrePersone;
    private String email;
    private String typeReservation;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user; // Relation avec l'utilisateur

    public ReserverForm() {
    }




    public ReserverForm(String adresse,
                        Integer dateHeure,
                        String firstName, String lastName,
                        int nombrePersone, String typeReservation,
                        String email,
                        String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.adresse = adresse;
        this.dateHeure = dateHeure;
        this.nombrePersone = nombrePersone;
        this.email = email;
        this.typeReservation = typeReservation;
    }

    public void setUserEmail(String userEmail) {
    }
}
