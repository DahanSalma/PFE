package pfe.serveur.partie_serveur.utilisateur.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.exception.EmailAlreadyExistsException;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public User save(UserDto userDto) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userDto.getEmail())) {
            try {
                throw new EmailAlreadyExistsException("L'email existe déjà : " + userDto.getEmail());
            } catch (EmailAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        User user = new User(userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole(), userDto.getFirstname(),userDto.getLastname());
        return userRepository.save(user);
    }

    @Override
    public void assignRolesOnRegistration(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + email);
        }
        // Assign roles based on existing role value
        if (user.getRole() == null) {
            user.setRole("USER");
        } else if (user.getRole().equals("ADMIN")) {
            // Do nothing, user already has ADMIN role
        } else {
            throw new IllegalArgumentException("Invalid role value: " + user.getRole());
        }
        // Update user role in the database
        userRepository.save(user);
    }

    @Override
    public boolean isUserLoggedIn() {
        return false;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        // Récupérer l'utilisateur à mettre à jour depuis la base de données
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        // Mettre à jour les champs de l'utilisateur avec les données de UserDto
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        existingUser.setRole(userDto.getRole());
        existingUser.setFirstname(userDto.getFirstname());
        existingUser.setLastname(userDto.getLastname());

        // Enregistrer les modifications dans la base de données
        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {

    }


    @Override
    public void save(User user) {
     userRepository.save(user);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        // Ajoutez ici toute autre logique nécessaire après la suppression de l'utilisateur.
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
        // Vous pouvez également ajouter une logique supplémentaire ici si nécessaire.
    }



}