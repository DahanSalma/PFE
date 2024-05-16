package pfe.serveur.partie_serveur.mangementRestaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pfe.serveur.partie_serveur.mangementRestaurant.model.Diner;

public interface DinerRepository extends JpaRepository<Diner,Long>{

}
