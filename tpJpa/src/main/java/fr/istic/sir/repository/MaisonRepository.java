package fr.istic.sir.repository;

import fr.istic.sir.domain.Maison;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Maison entity.
 */
public interface MaisonRepository extends JpaRepository<Maison,Long> {

}
