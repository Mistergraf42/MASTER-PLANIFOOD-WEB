package web.projetdevwebavancer.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.*;
import web.projetdevwebavancer.Service.CarteService;
import web.projetdevwebavancer.Service.RestaurantService;
import web.projetdevwebavancer.Service.RestaurateurService;
import web.projetdevwebavancer.Service.UserService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Controller
public class CarteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarteService carteService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/edit-carte")
    public String editCarte(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }
        Carte carte = carteService.getCarte();
        m.addAttribute("carte", carte);
        m.addAttribute("content","editCarte");
        return "base";
    }


    @PostMapping("/add-carte")
    public String addCarte(Carte carte, RedirectAttributes r) {
        carteService.saveCarte(carte);
        r.addFlashAttribute("message", "Carte ajoutée avec succès !");
        return "redirect:/edit-carte";
    }









}
