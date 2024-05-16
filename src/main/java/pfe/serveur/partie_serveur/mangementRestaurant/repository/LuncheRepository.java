package pfe.serveur.partie_serveur.mangementRestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfe.serveur.partie_serveur.mangementRestaurant.model.Lunche;

public interface LuncheRepository extends JpaRepository<Lunche,Long> {
}
