package pfe.serveur.partie_serveur.utilisateur.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pfe.serveur.partie_serveur.utilisateur.model.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);


    boolean existsByEmail(String email);
}
