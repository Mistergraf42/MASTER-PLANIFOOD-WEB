package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Repository.CategorieRepository;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie saveCategorie(Categorie categorie) {
            return categorieRepository.save(categorie);
        }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }
}
