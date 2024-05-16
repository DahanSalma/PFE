package pfe.serveur.partie_serveur.reservation.reporsitory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import pfe.serveur.partie_serveur.reservation.model.ReserverForm;
import pfe.serveur.partie_serveur.utilisateur.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ReservationReporsitory
        extends JpaRepository<ReserverForm, Long> {

}
