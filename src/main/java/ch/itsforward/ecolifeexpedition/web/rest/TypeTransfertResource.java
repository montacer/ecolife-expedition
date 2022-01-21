package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TypeTransfert;
import ch.itsforward.ecolifeexpedition.repository.TypeTransfertRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TypeTransfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeTransfertResource {

    private final Logger log = LoggerFactory.getLogger(TypeTransfertResource.class);

    private static final String ENTITY_NAME = "typeTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeTransfertRepository typeTransfertRepository;

    public TypeTransfertResource(TypeTransfertRepository typeTransfertRepository) {
        this.typeTransfertRepository = typeTransfertRepository;
    }

    /**
     * {@code POST  /type-transferts} : Create a new typeTransfert.
     *
     * @param typeTransfert the typeTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeTransfert, or with status {@code 400 (Bad Request)} if the typeTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-transferts")
    public ResponseEntity<TypeTransfert> createTypeTransfert(@RequestBody TypeTransfert typeTransfert) throws URISyntaxException {
        log.debug("REST request to save TypeTransfert : {}", typeTransfert);
        if (typeTransfert.getId() != null) {
            throw new BadRequestAlertException("A new typeTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTransfert result = typeTransfertRepository.save(typeTransfert);
        return ResponseEntity.created(new URI("/api/type-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-transferts} : Updates an existing typeTransfert.
     *
     * @param typeTransfert the typeTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTransfert,
     * or with status {@code 400 (Bad Request)} if the typeTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-transferts")
    public ResponseEntity<TypeTransfert> updateTypeTransfert(@RequestBody TypeTransfert typeTransfert) throws URISyntaxException {
        log.debug("REST request to update TypeTransfert : {}", typeTransfert);
        if (typeTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTransfert result = typeTransfertRepository.save(typeTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-transferts} : get all the typeTransferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeTransferts in body.
     */
    @GetMapping("/type-transferts")
    public List<TypeTransfert> getAllTypeTransferts() {
        log.debug("REST request to get all TypeTransferts");
        return typeTransfertRepository.findAll();
    }

    /**
     * {@code GET  /type-transferts/:id} : get the "id" typeTransfert.
     *
     * @param id the id of the typeTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-transferts/{id}")
    public ResponseEntity<TypeTransfert> getTypeTransfert(@PathVariable Long id) {
        log.debug("REST request to get TypeTransfert : {}", id);
        Optional<TypeTransfert> typeTransfert = typeTransfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeTransfert);
    }

    /**
     * {@code DELETE  /type-transferts/:id} : delete the "id" typeTransfert.
     *
     * @param id the id of the typeTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-transferts/{id}")
    public ResponseEntity<Void> deleteTypeTransfert(@PathVariable Long id) {
        log.debug("REST request to delete TypeTransfert : {}", id);
        typeTransfertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
