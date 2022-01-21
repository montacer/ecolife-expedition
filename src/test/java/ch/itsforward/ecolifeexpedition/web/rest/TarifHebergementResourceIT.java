package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TarifHebergement;
import ch.itsforward.ecolifeexpedition.repository.TarifHebergementRepository;

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
 * Integration tests for the {@link TarifHebergementResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TarifHebergementResourceIT {

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    @Autowired
    private TarifHebergementRepository tarifHebergementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifHebergementMockMvc;

    private TarifHebergement tarifHebergement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifHebergement createEntity(EntityManager em) {
        TarifHebergement tarifHebergement = new TarifHebergement()
            .montantTTC(DEFAULT_MONTANT_TTC);
        return tarifHebergement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifHebergement createUpdatedEntity(EntityManager em) {
        TarifHebergement tarifHebergement = new TarifHebergement()
            .montantTTC(UPDATED_MONTANT_TTC);
        return tarifHebergement;
    }

    @BeforeEach
    public void initTest() {
        tarifHebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifHebergement() throws Exception {
        int databaseSizeBeforeCreate = tarifHebergementRepository.findAll().size();
        // Create the TarifHebergement
        restTarifHebergementMockMvc.perform(post("/api/tarif-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergement)))
            .andExpect(status().isCreated());

        // Validate the TarifHebergement in the database
        List<TarifHebergement> tarifHebergementList = tarifHebergementRepository.findAll();
        assertThat(tarifHebergementList).hasSize(databaseSizeBeforeCreate + 1);
        TarifHebergement testTarifHebergement = tarifHebergementList.get(tarifHebergementList.size() - 1);
        assertThat(testTarifHebergement.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createTarifHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifHebergementRepository.findAll().size();

        // Create the TarifHebergement with an existing ID
        tarifHebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifHebergementMockMvc.perform(post("/api/tarif-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the TarifHebergement in the database
        List<TarifHebergement> tarifHebergementList = tarifHebergementRepository.findAll();
        assertThat(tarifHebergementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifHebergements() throws Exception {
        // Initialize the database
        tarifHebergementRepository.saveAndFlush(tarifHebergement);

        // Get all the tarifHebergementList
        restTarifHebergementMockMvc.perform(get("/api/tarif-hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifHebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifHebergement() throws Exception {
        // Initialize the database
        tarifHebergementRepository.saveAndFlush(tarifHebergement);

        // Get the tarifHebergement
        restTarifHebergementMockMvc.perform(get("/api/tarif-hebergements/{id}", tarifHebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifHebergement.getId().intValue()))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTarifHebergement() throws Exception {
        // Get the tarifHebergement
        restTarifHebergementMockMvc.perform(get("/api/tarif-hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifHebergement() throws Exception {
        // Initialize the database
        tarifHebergementRepository.saveAndFlush(tarifHebergement);

        int databaseSizeBeforeUpdate = tarifHebergementRepository.findAll().size();

        // Update the tarifHebergement
        TarifHebergement updatedTarifHebergement = tarifHebergementRepository.findById(tarifHebergement.getId()).get();
        // Disconnect from session so that the updates on updatedTarifHebergement are not directly saved in db
        em.detach(updatedTarifHebergement);
        updatedTarifHebergement
            .montantTTC(UPDATED_MONTANT_TTC);

        restTarifHebergementMockMvc.perform(put("/api/tarif-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifHebergement)))
            .andExpect(status().isOk());

        // Validate the TarifHebergement in the database
        List<TarifHebergement> tarifHebergementList = tarifHebergementRepository.findAll();
        assertThat(tarifHebergementList).hasSize(databaseSizeBeforeUpdate);
        TarifHebergement testTarifHebergement = tarifHebergementList.get(tarifHebergementList.size() - 1);
        assertThat(testTarifHebergement.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifHebergement() throws Exception {
        int databaseSizeBeforeUpdate = tarifHebergementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifHebergementMockMvc.perform(put("/api/tarif-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the TarifHebergement in the database
        List<TarifHebergement> tarifHebergementList = tarifHebergementRepository.findAll();
        assertThat(tarifHebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifHebergement() throws Exception {
        // Initialize the database
        tarifHebergementRepository.saveAndFlush(tarifHebergement);

        int databaseSizeBeforeDelete = tarifHebergementRepository.findAll().size();

        // Delete the tarifHebergement
        restTarifHebergementMockMvc.perform(delete("/api/tarif-hebergements/{id}", tarifHebergement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifHebergement> tarifHebergementList = tarifHebergementRepository.findAll();
        assertThat(tarifHebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
