package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.DonneurOrdre;
import ch.itsforward.ecolifeexpedition.repository.DonneurOrdreRepository;
import ch.itsforward.ecolifeexpedition.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.DonneurOrdre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DonneurOrdreResource {

    private final Logger log = LoggerFactory.getLogger(DonneurOrdreResource.class);

    private static final String ENTITY_NAME = "donneurOrdre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonneurOrdreRepository donneurOrdreRepository;

    public DonneurOrdreResource(DonneurOrdreRepository donneurOrdreRepository) {
        this.donneurOrdreRepository = donneurOrdreRepository;
    }

    /**
     * {@code POST  /donneur-ordres} : Create a new donneurOrdre.
     *
     * @param donneurOrdre the donneurOrdre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donneurOrdre, or with status {@code 400 (Bad Request)} if the donneurOrdre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donneur-ordres")
    public ResponseEntity<DonneurOrdre> createDonneurOrdre(@RequestBody DonneurOrdre donneurOrdre) throws URISyntaxException {
        log.debug("REST request to save DonneurOrdre : {}", donneurOrdre);
        if (donneurOrdre.getId() != null) {
            throw new BadRequestAlertException("A new donneurOrdre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonneurOrdre result = donneurOrdreRepository.save(donneurOrdre);
        return ResponseEntity.created(new URI("/api/donneur-ordres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donneur-ordres} : Updates an existing donneurOrdre.
     *
     * @param donneurOrdre the donneurOrdre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donneurOrdre,
     * or with status {@code 400 (Bad Request)} if the donneurOrdre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donneurOrdre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donneur-ordres")
    public ResponseEntity<DonneurOrdre> updateDonneurOrdre(@RequestBody DonneurOrdre donneurOrdre) throws URISyntaxException {
        log.debug("REST request to update DonneurOrdre : {}", donneurOrdre);
        if (donneurOrdre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DonneurOrdre result = donneurOrdreRepository.save(donneurOrdre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donneurOrdre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /donneur-ordres} : get all the donneurOrdres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donneurOrdres in body.
     */
    @GetMapping("/donneur-ordres")
    public List<DonneurOrdre> getAllDonneurOrdres() {
        log.debug("REST request to get all DonneurOrdres");
        return donneurOrdreRepository.findAll();
    }

    /**
     * {@code GET  /donneur-ordres/:id} : get the "id" donneurOrdre.
     *
     * @param id the id of the donneurOrdre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donneurOrdre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donneur-ordres/{id}")
    public ResponseEntity<DonneurOrdre> getDonneurOrdre(@PathVariable Long id) {
        log.debug("REST request to get DonneurOrdre : {}", id);
        Optional<DonneurOrdre> donneurOrdre = donneurOrdreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(donneurOrdre);
    }

    /**
     * {@code DELETE  /donneur-ordres/:id} : delete the "id" donneurOrdre.
     *
     * @param id the id of the donneurOrdre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donneur-ordres/{id}")
    public ResponseEntity<Void> deleteDonneurOrdre(@PathVariable Long id) {
        log.debug("REST request to delete DonneurOrdre : {}", id);
        donneurOrdreRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
