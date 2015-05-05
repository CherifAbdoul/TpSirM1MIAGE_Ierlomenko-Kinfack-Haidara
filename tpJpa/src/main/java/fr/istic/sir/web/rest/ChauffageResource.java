package fr.istic.sir.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sir.domain.Chauffage;
import fr.istic.sir.repository.ChauffageRepository;
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
 * REST controller for managing Chauffage.
 */
@RestController
@RequestMapping("/api")
public class ChauffageResource {

    private final Logger log = LoggerFactory.getLogger(ChauffageResource.class);

    @Inject
    private ChauffageRepository chauffageRepository;

    /**
     * POST  /chauffages -> Create a new chauffage.
     */
    @RequestMapping(value = "/chauffages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Chauffage chauffage) throws URISyntaxException {
        log.debug("REST request to save Chauffage : {}", chauffage);
        if (chauffage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new chauffage cannot already have an ID").build();
        }
        chauffageRepository.save(chauffage);
        return ResponseEntity.created(new URI("/api/chauffages/" + chauffage.getId())).build();
    }

    /**
     * PUT  /chauffages -> Updates an existing chauffage.
     */
    @RequestMapping(value = "/chauffages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Chauffage chauffage) throws URISyntaxException {
        log.debug("REST request to update Chauffage : {}", chauffage);
        if (chauffage.getId() == null) {
            return create(chauffage);
        }
        chauffageRepository.save(chauffage);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /chauffages -> get all the chauffages.
     */
    @RequestMapping(value = "/chauffages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Chauffage>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Chauffage> page = chauffageRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chauffages", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chauffages/:id -> get the "id" chauffage.
     */
    @RequestMapping(value = "/chauffages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Chauffage> get(@PathVariable Long id) {
        log.debug("REST request to get Chauffage : {}", id);
        return Optional.ofNullable(chauffageRepository.findOne(id))
            .map(chauffage -> new ResponseEntity<>(
                chauffage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chauffages/:id -> delete the "id" chauffage.
     */
    @RequestMapping(value = "/chauffages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Chauffage : {}", id);
        chauffageRepository.delete(id);
    }
}
