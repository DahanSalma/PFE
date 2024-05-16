package pfe.serveur.partie_serveur.mangementRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pfe.serveur.partie_serveur.mangementRestaurant.model.Breakfast;
import pfe.serveur.partie_serveur.mangementRestaurant.model.Diner;
import pfe.serveur.partie_serveur.mangementRestaurant.model.Lunche;
import pfe.serveur.partie_serveur.mangementRestaurant.repository.BreakfsatRepository;
import pfe.serveur.partie_serveur.mangementRestaurant.repository.DinerRepository;
import pfe.serveur.partie_serveur.mangementRestaurant.repository.LuncheRepository;


import java.util.List;

@Controller
public  class MenuController {

    @Autowired
    private BreakfsatRepository breakfsatRepository;
    @Autowired
    private LuncheRepository luncheRepository;

    @Autowired
    private DinerRepository dinerRepository;

    //Controller pour le petite-dejeune affichage dans la page
    @GetMapping("/breakfast")
    public String showAllBreakfast(Model model) {
        List<Breakfast> breakfasts = breakfsatRepository.findAll();
        model.addAttribute("breakfasts", breakfasts);
        return "pgeBrec";
    }

    //Controller pour le dejeune affichage dans la page lunche
    @GetMapping("/lunche")
    public String showAllLunche(Model model) {
        List<Lunche> lunches = luncheRepository.findAll();
        model.addAttribute("lunches", lunches);
        return "lunche";
    }

    @GetMapping("/diner")
    public String showAllDiner(Model model) {
        List<Diner> diners = dinerRepository.findAll();
        model.addAttribute("diners", diners);
        return "diner";
    }

    //Controller pour le petite-dejeune affichage dans la page de Admine
    @GetMapping("/admin-page/breakfsat")
    public String showBreakfast(Model model) {
        List<Breakfast> Listofbreakfasts = breakfsatRepository.findAll();
        model.addAttribute("breakfasts", Listofbreakfasts);
        return "breakfast";
    }

    //Controller pour le dejeune affichage dans la page Admine
    @GetMapping("/admin-page/lunche")
    public String showLunche(Model model) {
        List<Lunche> lunches = luncheRepository.findAll();
        model.addAttribute("lunches", lunches);
        return "lunch";
    }
    @GetMapping("/admin-page/diner")
    public String showDiner(Model model) {
        List<Diner> diners= dinerRepository.findAll();
        model.addAttribute("diners", diners);
        return "dineer";
    }
}




