package pfe.serveur.partie_serveur.reservation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pfe.serveur.partie_serveur.reservation.model.ReserverForm;
import pfe.serveur.partie_serveur.reservation.reporsitory.ReservationReporsitory;

import pfe.serveur.partie_serveur.utilisateur.repository.UserRepository;



import java.util.List;


@Controller
public class ReservationController {

    @Autowired
    private ReservationReporsitory reservationReporsitory;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reservation")
    public String getReservationPage(@ModelAttribute("reserverForm")
                                         ReserverForm reserverForm) {
        return "reserve";
    }

    @PostMapping("/reservation")
    public String saveReservation(@ModelAttribute("reserverForm") ReserverForm reserverForm, Model model) {
        // Here you should use the received reserverForm, not create a new one
        reservationReporsitory.save(reserverForm);
        model.addAttribute("msg", "Reservation Successfully");
        return "reserve";
    }


    @GetMapping("/admin-page/reserves")
    public String ReserverPage(Model model) {
        List<ReserverForm> listOfReserver = reservationReporsitory.findAll();
        model.addAttribute("reserverForm", listOfReserver);
        return "tableRes";
    }
}













