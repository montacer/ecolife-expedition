package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeTarif;
import ch.itsforward.ecolifeexpedition.repository.TypeTarifRepository;

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
 * Integration tests for the {@link TypeTarifResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeTarifResourceIT {

    private static final String DEFAULT_LIB_TYPE_TARIF = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TYPE_TARIF = "BBBBBBBBBB";

    @Autowired
    private TypeTarifRepository typeTarifRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeTarifMockMvc;

    private TypeTarif typeTarif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTarif createEntity(EntityManager em) {
        TypeTarif typeTarif = new TypeTarif()
            .libTypeTarif(DEFAULT_LIB_TYPE_TARIF);
        return typeTarif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTarif createUpdatedEntity(EntityManager em) {
        TypeTarif typeTarif = new TypeTarif()
            .libTypeTarif(UPDATED_LIB_TYPE_TARIF);
        return typeTarif;
    }

    @BeforeEach
    public void initTest() {
        typeTarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTarif() throws Exception {
        int databaseSizeBeforeCreate = typeTarifRepository.findAll().size();
        // Create the TypeTarif
        restTypeTarifMockMvc.perform(post("/api/type-tarifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isCreated());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTarif testTypeTarif = typeTarifList.get(typeTarifList.size() - 1);
        assertThat(testTypeTarif.getLibTypeTarif()).isEqualTo(DEFAULT_LIB_TYPE_TARIF);
    }

    @Test
    @Transactional
    public void createTypeTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTarifRepository.findAll().size();

        // Create the TypeTarif with an existing ID
        typeTarif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTarifMockMvc.perform(post("/api/type-tarifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeTarifs() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        // Get all the typeTarifList
        restTypeTarifMockMvc.perform(get("/api/type-tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTypeTarif").value(hasItem(DEFAULT_LIB_TYPE_TARIF)));
    }
    
    @Test
    @Transactional
    public void getTypeTarif() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        // Get the typeTarif
        restTypeTarifMockMvc.perform(get("/api/type-tarifs/{id}", typeTarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeTarif.getId().intValue()))
            .andExpect(jsonPath("$.libTypeTarif").value(DEFAULT_LIB_TYPE_TARIF));
    }
    @Test
    @Transactional
    public void getNonExistingTypeTarif() throws Exception {
        // Get the typeTarif
        restTypeTarifMockMvc.perform(get("/api/type-tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTarif() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        int databaseSizeBeforeUpdate = typeTarifRepository.findAll().size();

        // Update the typeTarif
        TypeTarif updatedTypeTarif = typeTarifRepository.findById(typeTarif.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTarif are not directly saved in db
        em.detach(updatedTypeTarif);
        updatedTypeTarif
            .libTypeTarif(UPDATED_LIB_TYPE_TARIF);

        restTypeTarifMockMvc.perform(put("/api/type-tarifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeTarif)))
            .andExpect(status().isOk());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeUpdate);
        TypeTarif testTypeTarif = typeTarifList.get(typeTarifList.size() - 1);
        assertThat(testTypeTarif.getLibTypeTarif()).isEqualTo(UPDATED_LIB_TYPE_TARIF);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTarif() throws Exception {
        int databaseSizeBeforeUpdate = typeTarifRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTarifMockMvc.perform(put("/api/type-tarifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeTarif() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        int databaseSizeBeforeDelete = typeTarifRepository.findAll().size();

        // Delete the typeTarif
        restTypeTarifMockMvc.perform(delete("/api/type-tarifs/{id}", typeTarif.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
