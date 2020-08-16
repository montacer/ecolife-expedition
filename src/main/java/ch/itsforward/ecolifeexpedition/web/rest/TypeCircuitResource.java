package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;
import ch.itsforward.ecolifeexpedition.repository.TypeCircuitRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TypeCircuit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeCircuitResource {

    private final Logger log = LoggerFactory.getLogger(TypeCircuitResource.class);

    private static final String ENTITY_NAME = "typeCircuit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCircuitRepository typeCircuitRepository;

    public TypeCircuitResource(TypeCircuitRepository typeCircuitRepository) {
        this.typeCircuitRepository = typeCircuitRepository;
    }

    /**
     * {@code POST  /type-circuits} : Create a new typeCircuit.
     *
     * @param typeCircuit the typeCircuit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCircuit, or with status {@code 400 (Bad Request)} if the typeCircuit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-circuits")
    public ResponseEntity<TypeCircuit> createTypeCircuit(@RequestBody TypeCircuit typeCircuit) throws URISyntaxException {
        log.debug("REST request to save TypeCircuit : {}", typeCircuit);
        if (typeCircuit.getId() != null) {
            throw new BadRequestAlertException("A new typeCircuit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCircuit result = typeCircuitRepository.save(typeCircuit);
        return ResponseEntity.created(new URI("/api/type-circuits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-circuits} : Updates an existing typeCircuit.
     *
     * @param typeCircuit the typeCircuit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCircuit,
     * or with status {@code 400 (Bad Request)} if the typeCircuit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCircuit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-circuits")
    public ResponseEntity<TypeCircuit> updateTypeCircuit(@RequestBody TypeCircuit typeCircuit) throws URISyntaxException {
        log.debug("REST request to update TypeCircuit : {}", typeCircuit);
        if (typeCircuit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCircuit result = typeCircuitRepository.save(typeCircuit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCircuit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-circuits} : get all the typeCircuits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeCircuits in body.
     */
    @GetMapping("/type-circuits")
    public List<TypeCircuit> getAllTypeCircuits() {
        log.debug("REST request to get all TypeCircuits");
        return typeCircuitRepository.findAll();
    }

    /**
     * {@code GET  /type-circuits/:id} : get the "id" typeCircuit.
     *
     * @param id the id of the typeCircuit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCircuit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-circuits/{id}")
    public ResponseEntity<TypeCircuit> getTypeCircuit(@PathVariable Long id) {
        log.debug("REST request to get TypeCircuit : {}", id);
        Optional<TypeCircuit> typeCircuit = typeCircuitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeCircuit);
    }

    /**
     * {@code DELETE  /type-circuits/:id} : delete the "id" typeCircuit.
     *
     * @param id the id of the typeCircuit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-circuits/{id}")
    public ResponseEntity<Void> deleteTypeCircuit(@PathVariable Long id) {
        log.debug("REST request to delete TypeCircuit : {}", id);
        typeCircuitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
