package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeHebergement;
import ch.itsforward.ecolifeexpedition.repository.TypeHebergementRepository;

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
 * Integration tests for the {@link TypeHebergementResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeHebergementResourceIT {

    private static final String DEFAULT_LIB_TYPE_HEBERGEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TYPE_HEBERGEMENT = "BBBBBBBBBB";

    @Autowired
    private TypeHebergementRepository typeHebergementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeHebergementMockMvc;

    private TypeHebergement typeHebergement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeHebergement createEntity(EntityManager em) {
        TypeHebergement typeHebergement = new TypeHebergement()
            .libTypeHebergement(DEFAULT_LIB_TYPE_HEBERGEMENT);
        return typeHebergement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeHebergement createUpdatedEntity(EntityManager em) {
        TypeHebergement typeHebergement = new TypeHebergement()
            .libTypeHebergement(UPDATED_LIB_TYPE_HEBERGEMENT);
        return typeHebergement;
    }

    @BeforeEach
    public void initTest() {
        typeHebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeHebergement() throws Exception {
        int databaseSizeBeforeCreate = typeHebergementRepository.findAll().size();
        // Create the TypeHebergement
        restTypeHebergementMockMvc.perform(post("/api/type-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeHebergement)))
            .andExpect(status().isCreated());

        // Validate the TypeHebergement in the database
        List<TypeHebergement> typeHebergementList = typeHebergementRepository.findAll();
        assertThat(typeHebergementList).hasSize(databaseSizeBeforeCreate + 1);
        TypeHebergement testTypeHebergement = typeHebergementList.get(typeHebergementList.size() - 1);
        assertThat(testTypeHebergement.getLibTypeHebergement()).isEqualTo(DEFAULT_LIB_TYPE_HEBERGEMENT);
    }

    @Test
    @Transactional
    public void createTypeHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeHebergementRepository.findAll().size();

        // Create the TypeHebergement with an existing ID
        typeHebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeHebergementMockMvc.perform(post("/api/type-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the TypeHebergement in the database
        List<TypeHebergement> typeHebergementList = typeHebergementRepository.findAll();
        assertThat(typeHebergementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeHebergements() throws Exception {
        // Initialize the database
        typeHebergementRepository.saveAndFlush(typeHebergement);

        // Get all the typeHebergementList
        restTypeHebergementMockMvc.perform(get("/api/type-hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeHebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTypeHebergement").value(hasItem(DEFAULT_LIB_TYPE_HEBERGEMENT)));
    }
    
    @Test
    @Transactional
    public void getTypeHebergement() throws Exception {
        // Initialize the database
        typeHebergementRepository.saveAndFlush(typeHebergement);

        // Get the typeHebergement
        restTypeHebergementMockMvc.perform(get("/api/type-hebergements/{id}", typeHebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeHebergement.getId().intValue()))
            .andExpect(jsonPath("$.libTypeHebergement").value(DEFAULT_LIB_TYPE_HEBERGEMENT));
    }
    @Test
    @Transactional
    public void getNonExistingTypeHebergement() throws Exception {
        // Get the typeHebergement
        restTypeHebergementMockMvc.perform(get("/api/type-hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeHebergement() throws Exception {
        // Initialize the database
        typeHebergementRepository.saveAndFlush(typeHebergement);

        int databaseSizeBeforeUpdate = typeHebergementRepository.findAll().size();

        // Update the typeHebergement
        TypeHebergement updatedTypeHebergement = typeHebergementRepository.findById(typeHebergement.getId()).get();
        // Disconnect from session so that the updates on updatedTypeHebergement are not directly saved in db
        em.detach(updatedTypeHebergement);
        updatedTypeHebergement
            .libTypeHebergement(UPDATED_LIB_TYPE_HEBERGEMENT);

        restTypeHebergementMockMvc.perform(put("/api/type-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeHebergement)))
            .andExpect(status().isOk());

        // Validate the TypeHebergement in the database
        List<TypeHebergement> typeHebergementList = typeHebergementRepository.findAll();
        assertThat(typeHebergementList).hasSize(databaseSizeBeforeUpdate);
        TypeHebergement testTypeHebergement = typeHebergementList.get(typeHebergementList.size() - 1);
        assertThat(testTypeHebergement.getLibTypeHebergement()).isEqualTo(UPDATED_LIB_TYPE_HEBERGEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeHebergement() throws Exception {
        int databaseSizeBeforeUpdate = typeHebergementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeHebergementMockMvc.perform(put("/api/type-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the TypeHebergement in the database
        List<TypeHebergement> typeHebergementList = typeHebergementRepository.findAll();
        assertThat(typeHebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeHebergement() throws Exception {
        // Initialize the database
        typeHebergementRepository.saveAndFlush(typeHebergement);

        int databaseSizeBeforeDelete = typeHebergementRepository.findAll().size();

        // Delete the typeHebergement
        restTypeHebergementMockMvc.perform(delete("/api/type-hebergements/{id}", typeHebergement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeHebergement> typeHebergementList = typeHebergementRepository.findAll();
        assertThat(typeHebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
