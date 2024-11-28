package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setMdp(passwordEncoder.encode(user.getMdp()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (!user.isEnabled()) {
            return null;
        }
        return user;
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(currentPassword, user.getMdp())) {
            user.setMdp(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean verifPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getMdp());
    }

    public boolean updateUserInfo(User user, String prenom, String nom, String email, String telephone) {
        user.setNom(nom);
        user.setEmail(email);
        user.setPrenom(prenom);
        user.setTel(telephone);
        userRepository.save(user);
        return true;
    }

    public void updateConnexion(User user) {
        UserDetails userDetails = getUserByEmail(user.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
