package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CategorieService;

import java.util.List;

@Controller
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/edit-carte/categories", method = RequestMethod.GET)
    public String editCategories(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }

        List<Categorie> categories = categorieService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("content", "categorie");
        return "base";
    }

    @PostMapping("/edit-carte/add-categories")
    public String addCategorie(@ModelAttribute Categorie categorie, RedirectAttributes redirectAttributes) {
        categorieService.saveCategorie(categorie);
        redirectAttributes.addFlashAttribute("successMessage", "Catégorie ajoutée avec succès !");
        return "redirect:/edit-carte/categories";
    }


}
