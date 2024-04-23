package pfe.serveur.partie_serveur.utilisateur.service;



import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.model.User;

import java.util.List;

@Service
public interface UserService {


    User save(UserDto userDto);


    void assignRolesOnRegistration(String email);


    boolean isUserLoggedIn();


}


