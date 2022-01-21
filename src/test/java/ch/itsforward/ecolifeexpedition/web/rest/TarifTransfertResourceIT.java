package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TarifTransfert;
import ch.itsforward.ecolifeexpedition.repository.TarifTransfertRepository;

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
 * Integration tests for the {@link TarifTransfertResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TarifTransfertResourceIT {

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    @Autowired
    private TarifTransfertRepository tarifTransfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifTransfertMockMvc;

    private TarifTransfert tarifTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransfert createEntity(EntityManager em) {
        TarifTransfert tarifTransfert = new TarifTransfert()
            .montantTTC(DEFAULT_MONTANT_TTC);
        return tarifTransfert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransfert createUpdatedEntity(EntityManager em) {
        TarifTransfert tarifTransfert = new TarifTransfert()
            .montantTTC(UPDATED_MONTANT_TTC);
        return tarifTransfert;
    }

    @BeforeEach
    public void initTest() {
        tarifTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifTransfert() throws Exception {
        int databaseSizeBeforeCreate = tarifTransfertRepository.findAll().size();
        // Create the TarifTransfert
        restTarifTransfertMockMvc.perform(post("/api/tarif-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransfert)))
            .andExpect(status().isCreated());

        // Validate the TarifTransfert in the database
        List<TarifTransfert> tarifTransfertList = tarifTransfertRepository.findAll();
        assertThat(tarifTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        TarifTransfert testTarifTransfert = tarifTransfertList.get(tarifTransfertList.size() - 1);
        assertThat(testTarifTransfert.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createTarifTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifTransfertRepository.findAll().size();

        // Create the TarifTransfert with an existing ID
        tarifTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifTransfertMockMvc.perform(post("/api/tarif-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransfert in the database
        List<TarifTransfert> tarifTransfertList = tarifTransfertRepository.findAll();
        assertThat(tarifTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifTransferts() throws Exception {
        // Initialize the database
        tarifTransfertRepository.saveAndFlush(tarifTransfert);

        // Get all the tarifTransfertList
        restTarifTransfertMockMvc.perform(get("/api/tarif-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifTransfert() throws Exception {
        // Initialize the database
        tarifTransfertRepository.saveAndFlush(tarifTransfert);

        // Get the tarifTransfert
        restTarifTransfertMockMvc.perform(get("/api/tarif-transferts/{id}", tarifTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifTransfert.getId().intValue()))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTarifTransfert() throws Exception {
        // Get the tarifTransfert
        restTarifTransfertMockMvc.perform(get("/api/tarif-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifTransfert() throws Exception {
        // Initialize the database
        tarifTransfertRepository.saveAndFlush(tarifTransfert);

        int databaseSizeBeforeUpdate = tarifTransfertRepository.findAll().size();

        // Update the tarifTransfert
        TarifTransfert updatedTarifTransfert = tarifTransfertRepository.findById(tarifTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedTarifTransfert are not directly saved in db
        em.detach(updatedTarifTransfert);
        updatedTarifTransfert
            .montantTTC(UPDATED_MONTANT_TTC);

        restTarifTransfertMockMvc.perform(put("/api/tarif-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifTransfert)))
            .andExpect(status().isOk());

        // Validate the TarifTransfert in the database
        List<TarifTransfert> tarifTransfertList = tarifTransfertRepository.findAll();
        assertThat(tarifTransfertList).hasSize(databaseSizeBeforeUpdate);
        TarifTransfert testTarifTransfert = tarifTransfertList.get(tarifTransfertList.size() - 1);
        assertThat(testTarifTransfert.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifTransfert() throws Exception {
        int databaseSizeBeforeUpdate = tarifTransfertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifTransfertMockMvc.perform(put("/api/tarif-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransfert in the database
        List<TarifTransfert> tarifTransfertList = tarifTransfertRepository.findAll();
        assertThat(tarifTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifTransfert() throws Exception {
        // Initialize the database
        tarifTransfertRepository.saveAndFlush(tarifTransfert);

        int databaseSizeBeforeDelete = tarifTransfertRepository.findAll().size();

        // Delete the tarifTransfert
        restTarifTransfertMockMvc.perform(delete("/api/tarif-transferts/{id}", tarifTransfert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifTransfert> tarifTransfertList = tarifTransfertRepository.findAll();
        assertThat(tarifTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
