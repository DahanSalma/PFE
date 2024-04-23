package pfe.serveur.partie_serveur.reservation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pfe.serveur.partie_serveur.reservation.dto.ReserverDto;
import pfe.serveur.partie_serveur.reservation.model.ReserverForm;
import pfe.serveur.partie_serveur.reservation.service.ReserverService;


import java.security.Principal;



@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReserverService reserverService;

    @GetMapping("/reservation")
    public String getReservationPage(@ModelAttribute("reserverForm") ReserverDto reserverDto,
                                     Principal principal, Model model) {
        // Vérifier si l'utilisateur est connecté
        if (principal != null) {
            // Utilisateur déjà connecté, rediriger vers le formulaire de réservation
            return "Reservation/reserve";
        } else {
            // Utilisateur non connecté, rediriger vers la page de connexion
            return "redirect:/login?redirect=/reservation"; //
            // Ajoutez un paramètre de redirection
        }
    }
    @PostMapping("/reservation")
    public String saveReservation(@ModelAttribute("reserverForm")
                                  ReserverDto reserverDto,
                                  BindingResult bindingResult,
                                  Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            // Gérer les erreurs de validation si nécessaire
            return "Reservation/reserve"; // Rediriger vers le formulaire de réservation avec les erreurs
        }

        // Vérifier si l'utilisateur est connecté
        if (principal != null) {
            // Récupérer l'email de l'utilisateur connecté
            String userEmail = principal.getName();

            // Associer l'email de l'utilisateur à la réservation
            reserverDto.setUserEmail(userEmail);

            // Enregistrer la réservation
            reserverService.save(reserverDto);
            model.addAttribute("msg", "Réservation effectuée avec succès !");
            return "redirect:/user-page"; // Rediriger vers la page utilisateur
        } else {
            // Utilisateur non connecté, rediriger vers la page de connexion
            return "redirect:/login?redirect=/reservation";
        }
    }









}


