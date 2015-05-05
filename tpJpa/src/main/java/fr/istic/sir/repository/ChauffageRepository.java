package fr.istic.sir.repository;

import fr.istic.sir.domain.Chauffage;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Chauffage entity.
 */
public interface ChauffageRepository extends JpaRepository<Chauffage,Long> {

}
