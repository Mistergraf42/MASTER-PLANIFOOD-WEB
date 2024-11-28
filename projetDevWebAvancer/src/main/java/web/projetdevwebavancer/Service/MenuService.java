package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.MenuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private  CategorieRepository categorieRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }


    public Menu updateMenu(Long id, Menu menuDetails) {
        return menuRepository.findById(id).map(menu -> {
            menu.setNom(menuDetails.getNom());
            menu.setDescription(menuDetails.getDescription());
            return menuRepository.save(menu);
        }).orElseThrow(() -> new RuntimeException("Menu introuvable."));
    }
}
