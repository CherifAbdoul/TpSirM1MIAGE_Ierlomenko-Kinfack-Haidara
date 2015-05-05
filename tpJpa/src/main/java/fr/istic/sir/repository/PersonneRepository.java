package fr.istic.sir.repository;

import fr.istic.sir.domain.Person;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personne entity.
 */
public interface PersonneRepository extends JpaRepository<Person,Long> {

}
