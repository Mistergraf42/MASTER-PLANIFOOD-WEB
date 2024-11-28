package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CategorieService;
import web.projetdevwebavancer.Service.MenuService;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/edit-carte/categories/{id}", method = RequestMethod.GET)
    public String getAllMenus(@PathVariable("id") Long id,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
        List<Menu> menus = menuService.getAllMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("content", "menu");

        return "base";
    }

    @PostMapping("/edit-carte/add-menus")
    public String createMenu(@PathVariable("id") Long categorieId, @ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {
        System.out.println(" creation d'un pour menu pour la categorieId: " + categorieId);
        Categorie categorie = categorieService.getCategorieById(categorieId);
        menu.setCategorie(categorie);

        menuService.saveMenu(menu);

        return "redirect:/edit-carte/categories/" + categorieId;
    }


    @GetMapping("/{id}")
    public String getMenuDetails(@PathVariable Long id, Model model) {
        model.addAttribute("menu", menuService.getMenuById(id).orElseThrow(() -> new RuntimeException("Menu introuvable.")));
        return "menu-details";
    }

}
