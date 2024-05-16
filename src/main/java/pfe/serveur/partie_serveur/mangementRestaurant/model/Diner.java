package pfe.serveur.partie_serveur.mangementRestaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Controller;

@Controller
@Data
@Entity
@Table(name = "diner")
public class Diner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plat;
    private  String descript;
    private String prix;
}
