package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import web.projetdevwebavancer.Entity.TokenValidation;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.TokenValidationRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.MailService;
import web.projetdevwebavancer.Service.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenValidationRepository tokenValidationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model m) {
        m.addAttribute("content", "login");
        return "base";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model m) {
        m.addAttribute("content", "register");
        return "base";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(@ModelAttribute User user, Model m) {
        try {
            userService.registerUser(user);
            String token = UUID.randomUUID().toString();
            TokenValidation validation = new TokenValidation();
            validation.setToken(token);
            validation.setUser(user);
            validation.setValid(false);
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.plusDays(2);
            validation.setExpirationDate(dateTime);
            tokenValidationRepository.save(validation);
            String validationUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/validationToken")
                    .queryParam("token", token)
                    .toUriString();
            Map<String,Object> option = new HashMap<>();
            option.put("url", validationUrl);
            mailService.sendMail(user.getEmail(), "PlaniFood validation de compte" , "emailValidationCompte", option);
        } catch (Exception e) {
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/validationToken", method = RequestMethod.GET)
    public String validationToken(@RequestParam("token") String token, Model m) {
        TokenValidation tokenToValide = tokenValidationRepository.findByToken(token);
        if(null != tokenToValide && !tokenToValide.getValid()) {
            tokenToValide.setValid(true);
            tokenValidationRepository.save(tokenToValide);
            User user = userRepository.findById(tokenToValide.getUser().getId()).orElse(null);
            if(user != null) {
                user.setEnabled(true);
                System.out.println("passage de " + user.getNom() + " avec le statut de validation " + user.isEnabled());
                userRepository.save(user);
                m.addAttribute("valide", true);
            }
        } else { m.addAttribute("valide", false); }

        m.addAttribute("content", "validationCompte");
        return "base";
    }

}
