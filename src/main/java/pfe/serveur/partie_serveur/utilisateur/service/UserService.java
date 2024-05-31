package pfe.serveur.partie_serveur.utilisateur.service;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;

import java.util.List;

@Service

public interface UserService {




    User save(UserDto userDto);


    void assignRolesOnRegistration(String email);


    boolean isUserLoggedIn();
    List<User> getAllUsers();

    User getUserById(Long id);

    void updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    void save(User user);

    List<User> listAll();

    void delete(Long id);

    User findById(Long id);
}


