package fr.istic.sir.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sir.domain.Person;
import fr.istic.sir.repository.PersonneRepository;
import fr.istic.sir.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Personne.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    @Inject
    private PersonneRepository personneRepository;

    /**
     * POST  /personnes -> Create a new personne.
     */
    @RequestMapping(value = "/personnes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Person personne) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personne);
        if (personne.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new personne cannot already have an ID").build();
        }
        personneRepository.save(personne);
        return ResponseEntity.created(new URI("/api/personnes/" + personne.getId())).build();
    }

    /**
     * PUT  /personnes -> Updates an existing personne.
     */
    @RequestMapping(value = "/personnes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Person personne) throws URISyntaxException {
        log.debug("REST request to update Personne : {}", personne);
        if (personne.getId() == null) {
            return create(personne);
        }
        personneRepository.save(personne);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /personnes -> get all the personnes.
     */
    @RequestMapping(value = "/personnes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Person>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Person> page = personneRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personnes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personnes/:id -> get the "id" personne.
     */
    @RequestMapping(value = "/personnes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Person> get(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        return Optional.ofNullable(personneRepository.findOne(id))
            .map(personne -> new ResponseEntity<>(
                personne,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personnes/:id -> delete the "id" personne.
     */
    @RequestMapping(value = "/personnes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneRepository.delete(id);
    }
}
