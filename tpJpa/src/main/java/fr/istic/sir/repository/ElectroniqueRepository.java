package fr.istic.sir.repository;

import fr.istic.sir.domain.Electronique;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Electronique entity.
 */
public interface ElectroniqueRepository extends JpaRepository<Electronique,Long> {

}
