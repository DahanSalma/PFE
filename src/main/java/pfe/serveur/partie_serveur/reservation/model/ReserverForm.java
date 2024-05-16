package pfe.serveur.partie_serveur.reservation.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pfe.serveur.partie_serveur.utilisateur.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
@Entity
@Table(name = "resrvation", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class ReserverForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime dateHeure;
    private int nombrePersone;
    @Column(unique = true)
    private String email;
    private String typeReservation;








}
