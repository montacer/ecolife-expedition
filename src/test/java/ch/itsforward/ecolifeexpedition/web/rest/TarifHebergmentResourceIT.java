package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TarifHebergment;
import ch.itsforward.ecolifeexpedition.repository.TarifHebergmentRepository;

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
 * Integration tests for the {@link TarifHebergmentResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TarifHebergmentResourceIT {

    private static final String DEFAULT_LIB_TARIF_HERGEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TARIF_HERGEMENT = "BBBBBBBBBB";

    @Autowired
    private TarifHebergmentRepository tarifHebergmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifHebergmentMockMvc;

    private TarifHebergment tarifHebergment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifHebergment createEntity(EntityManager em) {
        TarifHebergment tarifHebergment = new TarifHebergment()
            .libTarifHergement(DEFAULT_LIB_TARIF_HERGEMENT);
        return tarifHebergment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifHebergment createUpdatedEntity(EntityManager em) {
        TarifHebergment tarifHebergment = new TarifHebergment()
            .libTarifHergement(UPDATED_LIB_TARIF_HERGEMENT);
        return tarifHebergment;
    }

    @BeforeEach
    public void initTest() {
        tarifHebergment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifHebergment() throws Exception {
        int databaseSizeBeforeCreate = tarifHebergmentRepository.findAll().size();
        // Create the TarifHebergment
        restTarifHebergmentMockMvc.perform(post("/api/tarif-hebergments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergment)))
            .andExpect(status().isCreated());

        // Validate the TarifHebergment in the database
        List<TarifHebergment> tarifHebergmentList = tarifHebergmentRepository.findAll();
        assertThat(tarifHebergmentList).hasSize(databaseSizeBeforeCreate + 1);
        TarifHebergment testTarifHebergment = tarifHebergmentList.get(tarifHebergmentList.size() - 1);
        assertThat(testTarifHebergment.getLibTarifHergement()).isEqualTo(DEFAULT_LIB_TARIF_HERGEMENT);
    }

    @Test
    @Transactional
    public void createTarifHebergmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifHebergmentRepository.findAll().size();

        // Create the TarifHebergment with an existing ID
        tarifHebergment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifHebergmentMockMvc.perform(post("/api/tarif-hebergments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergment)))
            .andExpect(status().isBadRequest());

        // Validate the TarifHebergment in the database
        List<TarifHebergment> tarifHebergmentList = tarifHebergmentRepository.findAll();
        assertThat(tarifHebergmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifHebergments() throws Exception {
        // Initialize the database
        tarifHebergmentRepository.saveAndFlush(tarifHebergment);

        // Get all the tarifHebergmentList
        restTarifHebergmentMockMvc.perform(get("/api/tarif-hebergments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifHebergment.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTarifHergement").value(hasItem(DEFAULT_LIB_TARIF_HERGEMENT)));
    }
    
    @Test
    @Transactional
    public void getTarifHebergment() throws Exception {
        // Initialize the database
        tarifHebergmentRepository.saveAndFlush(tarifHebergment);

        // Get the tarifHebergment
        restTarifHebergmentMockMvc.perform(get("/api/tarif-hebergments/{id}", tarifHebergment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifHebergment.getId().intValue()))
            .andExpect(jsonPath("$.libTarifHergement").value(DEFAULT_LIB_TARIF_HERGEMENT));
    }
    @Test
    @Transactional
    public void getNonExistingTarifHebergment() throws Exception {
        // Get the tarifHebergment
        restTarifHebergmentMockMvc.perform(get("/api/tarif-hebergments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifHebergment() throws Exception {
        // Initialize the database
        tarifHebergmentRepository.saveAndFlush(tarifHebergment);

        int databaseSizeBeforeUpdate = tarifHebergmentRepository.findAll().size();

        // Update the tarifHebergment
        TarifHebergment updatedTarifHebergment = tarifHebergmentRepository.findById(tarifHebergment.getId()).get();
        // Disconnect from session so that the updates on updatedTarifHebergment are not directly saved in db
        em.detach(updatedTarifHebergment);
        updatedTarifHebergment
            .libTarifHergement(UPDATED_LIB_TARIF_HERGEMENT);

        restTarifHebergmentMockMvc.perform(put("/api/tarif-hebergments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifHebergment)))
            .andExpect(status().isOk());

        // Validate the TarifHebergment in the database
        List<TarifHebergment> tarifHebergmentList = tarifHebergmentRepository.findAll();
        assertThat(tarifHebergmentList).hasSize(databaseSizeBeforeUpdate);
        TarifHebergment testTarifHebergment = tarifHebergmentList.get(tarifHebergmentList.size() - 1);
        assertThat(testTarifHebergment.getLibTarifHergement()).isEqualTo(UPDATED_LIB_TARIF_HERGEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifHebergment() throws Exception {
        int databaseSizeBeforeUpdate = tarifHebergmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifHebergmentMockMvc.perform(put("/api/tarif-hebergments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifHebergment)))
            .andExpect(status().isBadRequest());

        // Validate the TarifHebergment in the database
        List<TarifHebergment> tarifHebergmentList = tarifHebergmentRepository.findAll();
        assertThat(tarifHebergmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifHebergment() throws Exception {
        // Initialize the database
        tarifHebergmentRepository.saveAndFlush(tarifHebergment);

        int databaseSizeBeforeDelete = tarifHebergmentRepository.findAll().size();

        // Delete the tarifHebergment
        restTarifHebergmentMockMvc.perform(delete("/api/tarif-hebergments/{id}", tarifHebergment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifHebergment> tarifHebergmentList = tarifHebergmentRepository.findAll();
        assertThat(tarifHebergmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
