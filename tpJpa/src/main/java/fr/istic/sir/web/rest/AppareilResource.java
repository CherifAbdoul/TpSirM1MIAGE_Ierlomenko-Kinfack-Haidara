package fr.istic.sir.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sir.domain.Appareil;
import fr.istic.sir.repository.AppareilRepository;
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
 * REST controller for managing Appareil.
 */
@RestController
@RequestMapping("/api")
public class AppareilResource {

    private final Logger log = LoggerFactory.getLogger(AppareilResource.class);

    @Inject
    private AppareilRepository appareilRepository;

    /**
     * POST  /appareils -> Create a new appareil.
     */
    @RequestMapping(value = "/appareils",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Appareil appareil) throws URISyntaxException {
        log.debug("REST request to save Appareil : {}", appareil);
        if (appareil.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new appareil cannot already have an ID").build();
        }
        appareilRepository.save(appareil);
        return ResponseEntity.created(new URI("/api/appareils/" + appareil.getId())).build();
    }

    /**
     * PUT  /appareils -> Updates an existing appareil.
     */
    @RequestMapping(value = "/appareils",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Appareil appareil) throws URISyntaxException {
        log.debug("REST request to update Appareil : {}", appareil);
        if (appareil.getId() == null) {
            return create(appareil);
        }
        appareilRepository.save(appareil);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /appareils -> get all the appareils.
     */
    @RequestMapping(value = "/appareils",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Appareil>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Appareil> page = appareilRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appareils", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appareils/:id -> get the "id" appareil.
     */
    @RequestMapping(value = "/appareils/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appareil> get(@PathVariable Long id) {
        log.debug("REST request to get Appareil : {}", id);
        return Optional.ofNullable(appareilRepository.findOne(id))
            .map(appareil -> new ResponseEntity<>(
                appareil,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appareils/:id -> delete the "id" appareil.
     */
    @RequestMapping(value = "/appareils/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Appareil : {}", id);
        appareilRepository.delete(id);
    }
}
