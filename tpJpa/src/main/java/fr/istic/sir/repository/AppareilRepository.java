package fr.istic.sir.repository;

import fr.istic.sir.domain.Appareil;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appareil entity.
 */
public interface AppareilRepository extends JpaRepository<Appareil,Long> {

}
