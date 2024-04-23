package pfe.serveur.partie_serveur.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserverDto {

    private String firstName;
    private String lastName;
    private String phone;
    private String adresse;
    private int dateHeure;
    private int nombrePersone;
    private String email;
    private String typeReservation;
    private String userEmail; // Champ pour stocker l'email de l'utilisateur
}
