package pfe.serveur.partie_serveur.utilisateur.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String fullname;
    private String email;
    private String password;
    private String role;


    public UserDto(String email, String password, String role, String fullname) {

        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;


    }


}
