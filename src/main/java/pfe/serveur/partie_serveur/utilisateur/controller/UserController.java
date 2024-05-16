package pfe.serveur.partie_serveur.utilisateur.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pfe.serveur.partie_serveur.exception.EmailAlreadyExistsException;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pfe.serveur.partie_serveur.jwtutil.JwtTokenUtils;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;
import pfe.serveur.partie_serveur.utilisateur.service.UserService;


import java.security.Principal;
import java.util.List;
import java.util.UUID;




@Controller
public class UserController {

    @Autowired
    private UserService userService;
@Autowired
private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDetails userDetails;


    @GetMapping({"/", "/homepage"})
    public String home() {
        return "home"; // Return the name of your home page template
    }

    @PostMapping({"/", "/homepage"})
    public String homepage() {
        return "home"; // Return the name of your home page template
    }


    @GetMapping("/sign-up")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {

        return "signup";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        userService.assignRolesOnRegistration(userDto.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
        String jwt = JwtTokenUtils.generateToken(userDetails.getUsername(),
                UUID.randomUUID().toString(), false);
        model.addAttribute("jwt", jwt);

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin-page";
        } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))) {
            return "redirect:/login"; // Redirige vers la page de connexion
        } else {
            return "redirect:/user-page";
        }
    }


    @GetMapping("/login")
    public String getLoginPage() {
      return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User credentials) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getFirstname());
        String jwt = jwtTokenUtils.generateToken(userDetails.getUsername(),
                UUID.randomUUID().toString(), false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        // Redirige vers la page utilisateur après connexion réussie
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }


    @GetMapping("user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user";
    }

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/admin-page/users")
    public String showAllUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("user",users);
        return "users";
    }

    /*// Afficher tous les utilisateurs
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Assurez-vous d'avoir une vue user-list pour afficher la liste des utilisateurs
    }

    // Afficher les détails d'un utilisateur par ID
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-details"; // Assurez-vous d'avoir une vue user-details pour afficher les détails de l'utilisateur
    }

    // Ajouter un nouvel utilisateur
    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") UserDto userDto) {
        userService.save(userDto);
        return "redirect:/users";
    }

    // Mettre à jour les informations d'un utilisateur par ID
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserDto userDto) {
        userService.updateUser(id, userDto);
        return "redirect:/users";
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }*/
}




