package pfe.serveur.partie_serveur.utilisateur.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pfe.serveur.partie_serveur.reservation.model.ReserverForm;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pfe.serveur.partie_serveur.jwtutil.JwtTokenUtils;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;
import pfe.serveur.partie_serveur.utilisateur.service.CustomUserDetailsService;
import pfe.serveur.partie_serveur.utilisateur.service.UserService;


import java.security.Principal;
import java.util.List;
import java.util.UUID;




@Controller
public class UserController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
        return "/dashbord/admin";
    }
    @GetMapping("/profile")
    public String profile(@ModelAttribute("userForm") User user,Model model)  {
        model.addAttribute("user", user);
        return "profile";
    }
    //All liste des clients
    @GetMapping("/admin-page/users")
    public String showAllUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("user",users);
        return "users";
    }
    //update profile Clients
    @GetMapping("/updateProfile")
    public String updateProfile() {
        return "add-user";
    }



    @GetMapping("/add")
    public String showFormForAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add-user";
    }



    @GetMapping("/shawNewClientsForm")
    public String shawNewClientsForm(@ModelAttribute("userForm") User user) {

        return "new_clients";
    }

    @PostMapping("/saveClient")
    public String saveClient(@ModelAttribute("userForm") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("msg", "client bien ajouter");
        return "new_clients";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "add-user"; // Assurez-vous que le nom de la vue est correct ici.
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user, Model model) {
        user.setId(id);
        userService.save(user);
        model.addAttribute("msg", "client bien modifier");
        return "users"; // Assurez-vous que le chemin de redirection est correct.
    }


    @GetMapping("/delete/{id}")
    public String deleteUsers(@PathVariable("id") Long id, Model model) {
        userService.delete(id);
        return "users";
    }

    @GetMapping("/delete-confirm/{id}")
    public String getDeleteConfirm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "delete-confirm";
    }
}




