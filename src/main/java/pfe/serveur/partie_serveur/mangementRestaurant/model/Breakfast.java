package pfe.serveur.partie_serveur.mangementRestaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "breakfsat_type")
@IdClass(Breakfast.BreakfastId.class)
public class Breakfast {

    @Id
    @Column(name = "type_breakfsat")
    private String typeBreakfsat;

    @Id
    @Column(name = "name_plats")
    private String namePlats;

    @Column(name = "description")
    private String descrip;

    @Column(name = "prix")
    private String prix;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
public static class BreakfastId implements Serializable {
        private String typeBreakfsat;
        private  String namePlats;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BreakfastId breakfastId = (BreakfastId) o;
            return Objects.equals(typeBreakfsat, breakfastId.typeBreakfsat) &&
                    Objects.equals(namePlats, breakfastId.namePlats);
        }

    }

}
