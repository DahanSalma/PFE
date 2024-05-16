package pfe.serveur.partie_serveur.utilisateur.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;


    public UserDto(String email, String password, String role, String firstname,String lastname ) {
        this.firstname=firstname;
        this.lastname=lastname;
        this.email = email;
        this.password = password;
        this.role = role;


    }


}
