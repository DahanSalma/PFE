package pfe.serveur.partie_serveur.reservation.service;

import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.reservation.dto.ReserverDto;
@Service
public interface ReserverService {

    void save(ReserverDto reserverDto);
}
