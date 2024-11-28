package web.projetdevwebavancer.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Repository.RestaurantRepository;

import java.util.List;

@RestController
public class RestRestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/searchRestaurant")
    public List<Restaurant> searchRestaurant(@RequestParam("name") String name) {
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(name);
        for (Restaurant restaurant : restaurants) {
            restaurant.setRestaurateur(null);
            restaurant.setHoraire(null);
        }
        System.out.println(restaurants);
        return restaurants;
    }
}
