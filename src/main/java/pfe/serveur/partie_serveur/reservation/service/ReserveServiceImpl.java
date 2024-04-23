package pfe.serveur.partie_serveur.reservation.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.reservation.dto.ReserverDto;
import pfe.serveur.partie_serveur.reservation.model.ReserverForm;
import pfe.serveur.partie_serveur.reservation.reporsitory.ReservationReporsitory;

@Service
public class ReserveServiceImpl implements ReserverService {

    private final ReservationReporsitory reservationReporsitory;

    @Autowired
    public ReserveServiceImpl(ReservationReporsitory reservationReporsitory) {
        this.reservationReporsitory=reservationReporsitory;
    }

    @Override
    public void save(ReserverDto reserverDto) {
        // Créer un objet ReserverForm à partir de ReserverDto
        ReserverForm reserverForm = new ReserverForm(reserverDto.getAdresse(),
                reserverDto.getDateHeure(), reserverDto.getFirstName(),
                reserverDto.getLastName(), reserverDto.getNombrePersone(),
                reserverDto.getTypeReservation(), reserverDto.getEmail(),
                reserverDto.getPhone());

        // Enregistrer l'objet ReserverForm dans la base de données en utilisant le repository
        reservationReporsitory.save(reserverForm);
    }
}

