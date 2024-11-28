package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableReserver;
import web.projetdevwebavancer.Entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TableReserverRepository extends JpaRepository<TableReserver, Long> {
    List<TableReserver> findAllByDateAndIdRestaurantAndNombreDePersonnes(LocalDate date, Long idRestaurant, int nombreDePersonnes);
    List<TableReserver> findAllByUser(User user);
    int countAllByDateBetweenAndIdRestaurant(LocalDate start, LocalDate end, Long idRestaurant);

}
