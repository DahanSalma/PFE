package pfe.serveur.partie_serveur.utilisateur.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole(), userDto.getFullname());
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




}