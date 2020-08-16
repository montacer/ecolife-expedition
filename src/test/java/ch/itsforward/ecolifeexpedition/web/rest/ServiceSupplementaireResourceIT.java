package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.ServiceSupplementaire;
import ch.itsforward.ecolifeexpedition.repository.ServiceSupplementaireRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceSupplementaireResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceSupplementaireResourceIT {

    private static final String DEFAULT_LIBELLE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SERVICE = "BBBBBBBBBB";

    @Autowired
    private ServiceSupplementaireRepository serviceSupplementaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceSupplementaireMockMvc;

    private ServiceSupplementaire serviceSupplementaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSupplementaire createEntity(EntityManager em) {
        ServiceSupplementaire serviceSupplementaire = new ServiceSupplementaire()
            .libelleService(DEFAULT_LIBELLE_SERVICE);
        return serviceSupplementaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSupplementaire createUpdatedEntity(EntityManager em) {
        ServiceSupplementaire serviceSupplementaire = new ServiceSupplementaire()
            .libelleService(UPDATED_LIBELLE_SERVICE);
        return serviceSupplementaire;
    }

    @BeforeEach
    public void initTest() {
        serviceSupplementaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceSupplementaire() throws Exception {
        int databaseSizeBeforeCreate = serviceSupplementaireRepository.findAll().size();
        // Create the ServiceSupplementaire
        restServiceSupplementaireMockMvc.perform(post("/api/service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupplementaire)))
            .andExpect(status().isCreated());

        // Validate the ServiceSupplementaire in the database
        List<ServiceSupplementaire> serviceSupplementaireList = serviceSupplementaireRepository.findAll();
        assertThat(serviceSupplementaireList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSupplementaire testServiceSupplementaire = serviceSupplementaireList.get(serviceSupplementaireList.size() - 1);
        assertThat(testServiceSupplementaire.getLibelleService()).isEqualTo(DEFAULT_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void createServiceSupplementaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSupplementaireRepository.findAll().size();

        // Create the ServiceSupplementaire with an existing ID
        serviceSupplementaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSupplementaireMockMvc.perform(post("/api/service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupplementaire)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupplementaire in the database
        List<ServiceSupplementaire> serviceSupplementaireList = serviceSupplementaireRepository.findAll();
        assertThat(serviceSupplementaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceSupplementaires() throws Exception {
        // Initialize the database
        serviceSupplementaireRepository.saveAndFlush(serviceSupplementaire);

        // Get all the serviceSupplementaireList
        restServiceSupplementaireMockMvc.perform(get("/api/service-supplementaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSupplementaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleService").value(hasItem(DEFAULT_LIBELLE_SERVICE)));
    }
    
    @Test
    @Transactional
    public void getServiceSupplementaire() throws Exception {
        // Initialize the database
        serviceSupplementaireRepository.saveAndFlush(serviceSupplementaire);

        // Get the serviceSupplementaire
        restServiceSupplementaireMockMvc.perform(get("/api/service-supplementaires/{id}", serviceSupplementaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSupplementaire.getId().intValue()))
            .andExpect(jsonPath("$.libelleService").value(DEFAULT_LIBELLE_SERVICE));
    }
    @Test
    @Transactional
    public void getNonExistingServiceSupplementaire() throws Exception {
        // Get the serviceSupplementaire
        restServiceSupplementaireMockMvc.perform(get("/api/service-supplementaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceSupplementaire() throws Exception {
        // Initialize the database
        serviceSupplementaireRepository.saveAndFlush(serviceSupplementaire);

        int databaseSizeBeforeUpdate = serviceSupplementaireRepository.findAll().size();

        // Update the serviceSupplementaire
        ServiceSupplementaire updatedServiceSupplementaire = serviceSupplementaireRepository.findById(serviceSupplementaire.getId()).get();
        // Disconnect from session so that the updates on updatedServiceSupplementaire are not directly saved in db
        em.detach(updatedServiceSupplementaire);
        updatedServiceSupplementaire
            .libelleService(UPDATED_LIBELLE_SERVICE);

        restServiceSupplementaireMockMvc.perform(put("/api/service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceSupplementaire)))
            .andExpect(status().isOk());

        // Validate the ServiceSupplementaire in the database
        List<ServiceSupplementaire> serviceSupplementaireList = serviceSupplementaireRepository.findAll();
        assertThat(serviceSupplementaireList).hasSize(databaseSizeBeforeUpdate);
        ServiceSupplementaire testServiceSupplementaire = serviceSupplementaireList.get(serviceSupplementaireList.size() - 1);
        assertThat(testServiceSupplementaire.getLibelleService()).isEqualTo(UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceSupplementaire() throws Exception {
        int databaseSizeBeforeUpdate = serviceSupplementaireRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceSupplementaireMockMvc.perform(put("/api/service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceSupplementaire)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceSupplementaire in the database
        List<ServiceSupplementaire> serviceSupplementaireList = serviceSupplementaireRepository.findAll();
        assertThat(serviceSupplementaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceSupplementaire() throws Exception {
        // Initialize the database
        serviceSupplementaireRepository.saveAndFlush(serviceSupplementaire);

        int databaseSizeBeforeDelete = serviceSupplementaireRepository.findAll().size();

        // Delete the serviceSupplementaire
        restServiceSupplementaireMockMvc.perform(delete("/api/service-supplementaires/{id}", serviceSupplementaire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceSupplementaire> serviceSupplementaireList = serviceSupplementaireRepository.findAll();
        assertThat(serviceSupplementaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
