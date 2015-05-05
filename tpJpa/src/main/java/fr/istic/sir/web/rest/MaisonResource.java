package fr.istic.sir.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sir.domain.Maison;
import fr.istic.sir.repository.MaisonRepository;
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
 * REST controller for managing Maison.
 */
@RestController
@RequestMapping("/api")
public class MaisonResource {

    private final Logger log = LoggerFactory.getLogger(MaisonResource.class);

    @Inject
    private MaisonRepository maisonRepository;

    /**
     * POST  /maisons -> Create a new maison.
     */
    @RequestMapping(value = "/maisons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Maison maison) throws URISyntaxException {
        log.debug("REST request to save Maison : {}", maison);
        if (maison.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new maison cannot already have an ID").build();
        }
        maisonRepository.save(maison);
        return ResponseEntity.created(new URI("/api/maisons/" + maison.getId())).build();
    }

    /**
     * PUT  /maisons -> Updates an existing maison.
     */
    @RequestMapping(value = "/maisons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Maison maison) throws URISyntaxException {
        log.debug("REST request to update Maison : {}", maison);
        if (maison.getId() == null) {
            return create(maison);
        }
        maisonRepository.save(maison);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /maisons -> get all the maisons.
     */
    @RequestMapping(value = "/maisons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Maison>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Maison> page = maisonRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maisons", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /maisons/:id -> get the "id" maison.
     */
    @RequestMapping(value = "/maisons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Maison> get(@PathVariable Long id) {
        log.debug("REST request to get Maison : {}", id);
        return Optional.ofNullable(maisonRepository.findOne(id))
            .map(maison -> new ResponseEntity<>(
                maison,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /maisons/:id -> delete the "id" maison.
     */
    @RequestMapping(value = "/maisons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Maison : {}", id);
        maisonRepository.delete(id);
    }
}
