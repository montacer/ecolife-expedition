package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TarifServiceSupplementaire;
import ch.itsforward.ecolifeexpedition.repository.TarifServiceSupplementaireRepository;

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
 * Integration tests for the {@link TarifServiceSupplementaireResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TarifServiceSupplementaireResourceIT {

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    @Autowired
    private TarifServiceSupplementaireRepository tarifServiceSupplementaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifServiceSupplementaireMockMvc;

    private TarifServiceSupplementaire tarifServiceSupplementaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifServiceSupplementaire createEntity(EntityManager em) {
        TarifServiceSupplementaire tarifServiceSupplementaire = new TarifServiceSupplementaire()
            .montantTTC(DEFAULT_MONTANT_TTC);
        return tarifServiceSupplementaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifServiceSupplementaire createUpdatedEntity(EntityManager em) {
        TarifServiceSupplementaire tarifServiceSupplementaire = new TarifServiceSupplementaire()
            .montantTTC(UPDATED_MONTANT_TTC);
        return tarifServiceSupplementaire;
    }

    @BeforeEach
    public void initTest() {
        tarifServiceSupplementaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifServiceSupplementaire() throws Exception {
        int databaseSizeBeforeCreate = tarifServiceSupplementaireRepository.findAll().size();
        // Create the TarifServiceSupplementaire
        restTarifServiceSupplementaireMockMvc.perform(post("/api/tarif-service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifServiceSupplementaire)))
            .andExpect(status().isCreated());

        // Validate the TarifServiceSupplementaire in the database
        List<TarifServiceSupplementaire> tarifServiceSupplementaireList = tarifServiceSupplementaireRepository.findAll();
        assertThat(tarifServiceSupplementaireList).hasSize(databaseSizeBeforeCreate + 1);
        TarifServiceSupplementaire testTarifServiceSupplementaire = tarifServiceSupplementaireList.get(tarifServiceSupplementaireList.size() - 1);
        assertThat(testTarifServiceSupplementaire.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createTarifServiceSupplementaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifServiceSupplementaireRepository.findAll().size();

        // Create the TarifServiceSupplementaire with an existing ID
        tarifServiceSupplementaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifServiceSupplementaireMockMvc.perform(post("/api/tarif-service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifServiceSupplementaire)))
            .andExpect(status().isBadRequest());

        // Validate the TarifServiceSupplementaire in the database
        List<TarifServiceSupplementaire> tarifServiceSupplementaireList = tarifServiceSupplementaireRepository.findAll();
        assertThat(tarifServiceSupplementaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifServiceSupplementaires() throws Exception {
        // Initialize the database
        tarifServiceSupplementaireRepository.saveAndFlush(tarifServiceSupplementaire);

        // Get all the tarifServiceSupplementaireList
        restTarifServiceSupplementaireMockMvc.perform(get("/api/tarif-service-supplementaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifServiceSupplementaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifServiceSupplementaire() throws Exception {
        // Initialize the database
        tarifServiceSupplementaireRepository.saveAndFlush(tarifServiceSupplementaire);

        // Get the tarifServiceSupplementaire
        restTarifServiceSupplementaireMockMvc.perform(get("/api/tarif-service-supplementaires/{id}", tarifServiceSupplementaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifServiceSupplementaire.getId().intValue()))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTarifServiceSupplementaire() throws Exception {
        // Get the tarifServiceSupplementaire
        restTarifServiceSupplementaireMockMvc.perform(get("/api/tarif-service-supplementaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifServiceSupplementaire() throws Exception {
        // Initialize the database
        tarifServiceSupplementaireRepository.saveAndFlush(tarifServiceSupplementaire);

        int databaseSizeBeforeUpdate = tarifServiceSupplementaireRepository.findAll().size();

        // Update the tarifServiceSupplementaire
        TarifServiceSupplementaire updatedTarifServiceSupplementaire = tarifServiceSupplementaireRepository.findById(tarifServiceSupplementaire.getId()).get();
        // Disconnect from session so that the updates on updatedTarifServiceSupplementaire are not directly saved in db
        em.detach(updatedTarifServiceSupplementaire);
        updatedTarifServiceSupplementaire
            .montantTTC(UPDATED_MONTANT_TTC);

        restTarifServiceSupplementaireMockMvc.perform(put("/api/tarif-service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifServiceSupplementaire)))
            .andExpect(status().isOk());

        // Validate the TarifServiceSupplementaire in the database
        List<TarifServiceSupplementaire> tarifServiceSupplementaireList = tarifServiceSupplementaireRepository.findAll();
        assertThat(tarifServiceSupplementaireList).hasSize(databaseSizeBeforeUpdate);
        TarifServiceSupplementaire testTarifServiceSupplementaire = tarifServiceSupplementaireList.get(tarifServiceSupplementaireList.size() - 1);
        assertThat(testTarifServiceSupplementaire.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifServiceSupplementaire() throws Exception {
        int databaseSizeBeforeUpdate = tarifServiceSupplementaireRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifServiceSupplementaireMockMvc.perform(put("/api/tarif-service-supplementaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifServiceSupplementaire)))
            .andExpect(status().isBadRequest());

        // Validate the TarifServiceSupplementaire in the database
        List<TarifServiceSupplementaire> tarifServiceSupplementaireList = tarifServiceSupplementaireRepository.findAll();
        assertThat(tarifServiceSupplementaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifServiceSupplementaire() throws Exception {
        // Initialize the database
        tarifServiceSupplementaireRepository.saveAndFlush(tarifServiceSupplementaire);

        int databaseSizeBeforeDelete = tarifServiceSupplementaireRepository.findAll().size();

        // Delete the tarifServiceSupplementaire
        restTarifServiceSupplementaireMockMvc.perform(delete("/api/tarif-service-supplementaires/{id}", tarifServiceSupplementaire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifServiceSupplementaire> tarifServiceSupplementaireList = tarifServiceSupplementaireRepository.findAll();
        assertThat(tarifServiceSupplementaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
