package fr.istic.sir.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.sir.domain.Electronique;
import fr.istic.sir.repository.ElectroniqueRepository;
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
 * REST controller for managing Electronique.
 */
@RestController
@RequestMapping("/api")
public class ElectroniqueResource {

    private final Logger log = LoggerFactory.getLogger(ElectroniqueResource.class);

    @Inject
    private ElectroniqueRepository electroniqueRepository;

    /**
     * POST  /electroniques -> Create a new electronique.
     */
    @RequestMapping(value = "/electroniques",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Electronique electronique) throws URISyntaxException {
        log.debug("REST request to save Electronique : {}", electronique);
        if (electronique.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new electronique cannot already have an ID").build();
        }
        electroniqueRepository.save(electronique);
        return ResponseEntity.created(new URI("/api/electroniques/" + electronique.getId())).build();
    }

    /**
     * PUT  /electroniques -> Updates an existing electronique.
     */
    @RequestMapping(value = "/electroniques",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Electronique electronique) throws URISyntaxException {
        log.debug("REST request to update Electronique : {}", electronique);
        if (electronique.getId() == null) {
            return create(electronique);
        }
        electroniqueRepository.save(electronique);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /electroniques -> get all the electroniques.
     */
    @RequestMapping(value = "/electroniques",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Electronique>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Electronique> page = electroniqueRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/electroniques", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /electroniques/:id -> get the "id" electronique.
     */
    @RequestMapping(value = "/electroniques/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Electronique> get(@PathVariable Long id) {
        log.debug("REST request to get Electronique : {}", id);
        return Optional.ofNullable(electroniqueRepository.findOne(id))
            .map(electronique -> new ResponseEntity<>(
                electronique,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /electroniques/:id -> delete the "id" electronique.
     */
    @RequestMapping(value = "/electroniques/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Electronique : {}", id);
        electroniqueRepository.delete(id);
    }
}
