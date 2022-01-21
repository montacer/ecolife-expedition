package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeRegion;
import ch.itsforward.ecolifeexpedition.repository.TypeRegionRepository;

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
 * Integration tests for the {@link TypeRegionResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeRegionResourceIT {

    private static final String DEFAULT_LIB_TYPE_REGION = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TYPE_REGION = "BBBBBBBBBB";

    @Autowired
    private TypeRegionRepository typeRegionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeRegionMockMvc;

    private TypeRegion typeRegion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRegion createEntity(EntityManager em) {
        TypeRegion typeRegion = new TypeRegion()
            .libTypeRegion(DEFAULT_LIB_TYPE_REGION);
        return typeRegion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeRegion createUpdatedEntity(EntityManager em) {
        TypeRegion typeRegion = new TypeRegion()
            .libTypeRegion(UPDATED_LIB_TYPE_REGION);
        return typeRegion;
    }

    @BeforeEach
    public void initTest() {
        typeRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRegion() throws Exception {
        int databaseSizeBeforeCreate = typeRegionRepository.findAll().size();
        // Create the TypeRegion
        restTypeRegionMockMvc.perform(post("/api/type-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRegion)))
            .andExpect(status().isCreated());

        // Validate the TypeRegion in the database
        List<TypeRegion> typeRegionList = typeRegionRepository.findAll();
        assertThat(typeRegionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRegion testTypeRegion = typeRegionList.get(typeRegionList.size() - 1);
        assertThat(testTypeRegion.getLibTypeRegion()).isEqualTo(DEFAULT_LIB_TYPE_REGION);
    }

    @Test
    @Transactional
    public void createTypeRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRegionRepository.findAll().size();

        // Create the TypeRegion with an existing ID
        typeRegion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRegionMockMvc.perform(post("/api/type-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRegion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRegion in the database
        List<TypeRegion> typeRegionList = typeRegionRepository.findAll();
        assertThat(typeRegionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeRegions() throws Exception {
        // Initialize the database
        typeRegionRepository.saveAndFlush(typeRegion);

        // Get all the typeRegionList
        restTypeRegionMockMvc.perform(get("/api/type-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTypeRegion").value(hasItem(DEFAULT_LIB_TYPE_REGION)));
    }
    
    @Test
    @Transactional
    public void getTypeRegion() throws Exception {
        // Initialize the database
        typeRegionRepository.saveAndFlush(typeRegion);

        // Get the typeRegion
        restTypeRegionMockMvc.perform(get("/api/type-regions/{id}", typeRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeRegion.getId().intValue()))
            .andExpect(jsonPath("$.libTypeRegion").value(DEFAULT_LIB_TYPE_REGION));
    }
    @Test
    @Transactional
    public void getNonExistingTypeRegion() throws Exception {
        // Get the typeRegion
        restTypeRegionMockMvc.perform(get("/api/type-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRegion() throws Exception {
        // Initialize the database
        typeRegionRepository.saveAndFlush(typeRegion);

        int databaseSizeBeforeUpdate = typeRegionRepository.findAll().size();

        // Update the typeRegion
        TypeRegion updatedTypeRegion = typeRegionRepository.findById(typeRegion.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRegion are not directly saved in db
        em.detach(updatedTypeRegion);
        updatedTypeRegion
            .libTypeRegion(UPDATED_LIB_TYPE_REGION);

        restTypeRegionMockMvc.perform(put("/api/type-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeRegion)))
            .andExpect(status().isOk());

        // Validate the TypeRegion in the database
        List<TypeRegion> typeRegionList = typeRegionRepository.findAll();
        assertThat(typeRegionList).hasSize(databaseSizeBeforeUpdate);
        TypeRegion testTypeRegion = typeRegionList.get(typeRegionList.size() - 1);
        assertThat(testTypeRegion.getLibTypeRegion()).isEqualTo(UPDATED_LIB_TYPE_REGION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRegion() throws Exception {
        int databaseSizeBeforeUpdate = typeRegionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRegionMockMvc.perform(put("/api/type-regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeRegion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRegion in the database
        List<TypeRegion> typeRegionList = typeRegionRepository.findAll();
        assertThat(typeRegionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeRegion() throws Exception {
        // Initialize the database
        typeRegionRepository.saveAndFlush(typeRegion);

        int databaseSizeBeforeDelete = typeRegionRepository.findAll().size();

        // Delete the typeRegion
        restTypeRegionMockMvc.perform(delete("/api/type-regions/{id}", typeRegion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeRegion> typeRegionList = typeRegionRepository.findAll();
        assertThat(typeRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
