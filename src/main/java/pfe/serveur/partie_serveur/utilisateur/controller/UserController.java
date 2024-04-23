package pfe.serveur.partie_serveur.utilisateur.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pfe.serveur.partie_serveur.jwtutil.JwtTokenUtils;
import pfe.serveur.partie_serveur.utilisateur.dto.UserDto;
import pfe.serveur.partie_serveur.utilisateur.service.UserService;


import java.security.Principal;
import java.util.List;
import java.util.UUID;




@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
        } else if (userDetails.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("RESTORE"))) {
            return "redirect:/restore-page";

        } else if (userDetails.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("USER"))) {
            return "redirect:/user-page";

        } else return "user-page";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        if (!userService.isUserLoggedIn()) { // Vérifie si l'utilisateur est déjà connecté
            return "login"; // Affiche la page de connexion si l'utilisateur n'est pas connecté
        } else {
            return "redirect:/reservation"; // Redirige vers la page de réservation si l'utilisateur est déjà connecté
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User credentials) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
        String jwt = jwtTokenUtils.generateToken(userDetails.getUsername(), UUID.randomUUID().toString(), false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user";
    }

    @GetMapping("admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/user-profile")
    public String profilePage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "profile";
    }
}




