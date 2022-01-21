package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TypeHebergement;
import ch.itsforward.ecolifeexpedition.repository.TypeHebergementRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TypeHebergement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeHebergementResource {

    private final Logger log = LoggerFactory.getLogger(TypeHebergementResource.class);

    private static final String ENTITY_NAME = "typeHebergement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeHebergementRepository typeHebergementRepository;

    public TypeHebergementResource(TypeHebergementRepository typeHebergementRepository) {
        this.typeHebergementRepository = typeHebergementRepository;
    }

    /**
     * {@code POST  /type-hebergements} : Create a new typeHebergement.
     *
     * @param typeHebergement the typeHebergement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeHebergement, or with status {@code 400 (Bad Request)} if the typeHebergement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-hebergements")
    public ResponseEntity<TypeHebergement> createTypeHebergement(@RequestBody TypeHebergement typeHebergement) throws URISyntaxException {
        log.debug("REST request to save TypeHebergement : {}", typeHebergement);
        if (typeHebergement.getId() != null) {
            throw new BadRequestAlertException("A new typeHebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeHebergement result = typeHebergementRepository.save(typeHebergement);
        return ResponseEntity.created(new URI("/api/type-hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-hebergements} : Updates an existing typeHebergement.
     *
     * @param typeHebergement the typeHebergement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeHebergement,
     * or with status {@code 400 (Bad Request)} if the typeHebergement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeHebergement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-hebergements")
    public ResponseEntity<TypeHebergement> updateTypeHebergement(@RequestBody TypeHebergement typeHebergement) throws URISyntaxException {
        log.debug("REST request to update TypeHebergement : {}", typeHebergement);
        if (typeHebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeHebergement result = typeHebergementRepository.save(typeHebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeHebergement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-hebergements} : get all the typeHebergements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeHebergements in body.
     */
    @GetMapping("/type-hebergements")
    public List<TypeHebergement> getAllTypeHebergements() {
        log.debug("REST request to get all TypeHebergements");
        return typeHebergementRepository.findAll();
    }

    /**
     * {@code GET  /type-hebergements/:id} : get the "id" typeHebergement.
     *
     * @param id the id of the typeHebergement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeHebergement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-hebergements/{id}")
    public ResponseEntity<TypeHebergement> getTypeHebergement(@PathVariable Long id) {
        log.debug("REST request to get TypeHebergement : {}", id);
        Optional<TypeHebergement> typeHebergement = typeHebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeHebergement);
    }

    /**
     * {@code DELETE  /type-hebergements/:id} : delete the "id" typeHebergement.
     *
     * @param id the id of the typeHebergement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-hebergements/{id}")
    public ResponseEntity<Void> deleteTypeHebergement(@PathVariable Long id) {
        log.debug("REST request to delete TypeHebergement : {}", id);
        typeHebergementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
