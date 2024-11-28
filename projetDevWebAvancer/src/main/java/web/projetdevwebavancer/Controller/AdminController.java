package web.projetdevwebavancer.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String dashboard(Model m) throws MessagingException {

        m.addAttribute("content", "accueilAdmin");
        return "adminBase";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.GET)
    public String utilisateur(Model m) throws MessagingException {
        List<User> users = userRepository.findAll();

        List<Map<String, ? extends Serializable>> usersDto = users.stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "nom", user.getNom(),
                        "email", user.getEmail(),
                        "enabled", user.isEnabled()
                ))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String usersJson = objectMapper.writeValueAsString(usersDto);
            m.addAttribute("users", usersJson);
        } catch (Exception e) {

        }
        m.addAttribute("content", "utilisateurAdmin");
        return "adminBase";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/support", method = RequestMethod.GET)
    public String support(Model m) throws MessagingException {

        m.addAttribute("content", "accueilAdmin");
        return "adminBase";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.POST)
    public String switchEnabeled(@RequestParam("id") Long id){
        System.out.println("Arrivée ici");
        User user = userRepository.findById(id).get();
        if(user.isEnabled()){
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userRepository.save(user);
        return "redirect:/admin/utilisateur";
    }
}
