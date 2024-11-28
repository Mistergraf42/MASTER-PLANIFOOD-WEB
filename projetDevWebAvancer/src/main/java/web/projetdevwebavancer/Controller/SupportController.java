package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.Ticket;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.MessageRepository;
import web.projetdevwebavancer.Repository.TicketRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.TicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class SupportController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    TicketService ticketService;



    @RequestMapping(value = "/mes_ticket", method = RequestMethod.GET)
    public String pageMesTicket(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("tickets",user.getTickets());
        }

        m.addAttribute("content", "voirTicket");
        return "base";
    }


    @RequestMapping(value = "/creationTicket", method = RequestMethod.GET)
    public String pageTicket(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
         //   m.addAttribute("ticket",user.getTickets());
        }
        m.addAttribute("content", "creationTicket");
        return "base";
    }


    @RequestMapping(value = "/creationTicket", method = RequestMethod.POST)
    public String configRestaurantPost(@RequestParam("titre") String titre,
                                       @RequestParam("description") String description
                                       ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Ticket ticket = new Ticket();
            ticket.setDescription(description);
            ticket.setTitre(titre);
            ticket.setStatus(ticket.OUVERT); // 0 mean its open
            ticket.setPrioriter(ticket.FAIBLE);
            ticket.setDateCreation(LocalDateTime.now());
            ticket.setDateModification(LocalDateTime.now());
            ticket.setIdClient(user);
            user.getTickets().add(ticket);
            ticketRepository.save(ticket);
            userRepository.save(user);

        }
        return "redirect:/creationTicket";
    }




//    @RequestMapping(value = "/chatTicket", method = RequestMethod.GET)
//    public String pageMesChatTicket(Model m) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String email = authentication.getName();
//            User user = userRepository.findByEmail(email);
//            m.addAttribute("user", user);
//            m.addAttribute("tickets",user.getTickets());
//        }
//
//        m.addAttribute("content", "chatTicket");
//        return "base";
//    }

    @RequestMapping(value = "/chatTicket/{id}", method = RequestMethod.GET)
    public String chat(Model m, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("tickets",user.getTickets());
            m.addAttribute("messages",ticketService.messages(id));
            System.out.println(ticketService.messages(id));
            System.out.println("abc");

        }
        m.addAttribute("content", "chatTicket");
        return "base";
    }



}
