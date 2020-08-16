package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.ServiceSupplementaire;
import ch.itsforward.ecolifeexpedition.repository.ServiceSupplementaireRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.ServiceSupplementaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceSupplementaireResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSupplementaireResource.class);

    private static final String ENTITY_NAME = "serviceSupplementaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceSupplementaireRepository serviceSupplementaireRepository;

    public ServiceSupplementaireResource(ServiceSupplementaireRepository serviceSupplementaireRepository) {
        this.serviceSupplementaireRepository = serviceSupplementaireRepository;
    }

    /**
     * {@code POST  /service-supplementaires} : Create a new serviceSupplementaire.
     *
     * @param serviceSupplementaire the serviceSupplementaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceSupplementaire, or with status {@code 400 (Bad Request)} if the serviceSupplementaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-supplementaires")
    public ResponseEntity<ServiceSupplementaire> createServiceSupplementaire(@RequestBody ServiceSupplementaire serviceSupplementaire) throws URISyntaxException {
        log.debug("REST request to save ServiceSupplementaire : {}", serviceSupplementaire);
        if (serviceSupplementaire.getId() != null) {
            throw new BadRequestAlertException("A new serviceSupplementaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSupplementaire result = serviceSupplementaireRepository.save(serviceSupplementaire);
        return ResponseEntity.created(new URI("/api/service-supplementaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-supplementaires} : Updates an existing serviceSupplementaire.
     *
     * @param serviceSupplementaire the serviceSupplementaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSupplementaire,
     * or with status {@code 400 (Bad Request)} if the serviceSupplementaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceSupplementaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-supplementaires")
    public ResponseEntity<ServiceSupplementaire> updateServiceSupplementaire(@RequestBody ServiceSupplementaire serviceSupplementaire) throws URISyntaxException {
        log.debug("REST request to update ServiceSupplementaire : {}", serviceSupplementaire);
        if (serviceSupplementaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceSupplementaire result = serviceSupplementaireRepository.save(serviceSupplementaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSupplementaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-supplementaires} : get all the serviceSupplementaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSupplementaires in body.
     */
    @GetMapping("/service-supplementaires")
    public List<ServiceSupplementaire> getAllServiceSupplementaires() {
        log.debug("REST request to get all ServiceSupplementaires");
        return serviceSupplementaireRepository.findAll();
    }

    /**
     * {@code GET  /service-supplementaires/:id} : get the "id" serviceSupplementaire.
     *
     * @param id the id of the serviceSupplementaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSupplementaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-supplementaires/{id}")
    public ResponseEntity<ServiceSupplementaire> getServiceSupplementaire(@PathVariable Long id) {
        log.debug("REST request to get ServiceSupplementaire : {}", id);
        Optional<ServiceSupplementaire> serviceSupplementaire = serviceSupplementaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceSupplementaire);
    }

    /**
     * {@code DELETE  /service-supplementaires/:id} : delete the "id" serviceSupplementaire.
     *
     * @param id the id of the serviceSupplementaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-supplementaires/{id}")
    public ResponseEntity<Void> deleteServiceSupplementaire(@PathVariable Long id) {
        log.debug("REST request to delete ServiceSupplementaire : {}", id);
        serviceSupplementaireRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
