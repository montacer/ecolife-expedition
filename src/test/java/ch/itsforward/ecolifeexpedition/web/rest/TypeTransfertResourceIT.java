package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeTransfert;
import ch.itsforward.ecolifeexpedition.repository.TypeTransfertRepository;

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
 * Integration tests for the {@link TypeTransfertResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeTransfertResourceIT {

    private static final String DEFAULT_LIB_TYPE_TRANSFERT = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TYPE_TRANSFERT = "BBBBBBBBBB";

    @Autowired
    private TypeTransfertRepository typeTransfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeTransfertMockMvc;

    private TypeTransfert typeTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTransfert createEntity(EntityManager em) {
        TypeTransfert typeTransfert = new TypeTransfert()
            .libTypeTransfert(DEFAULT_LIB_TYPE_TRANSFERT);
        return typeTransfert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTransfert createUpdatedEntity(EntityManager em) {
        TypeTransfert typeTransfert = new TypeTransfert()
            .libTypeTransfert(UPDATED_LIB_TYPE_TRANSFERT);
        return typeTransfert;
    }

    @BeforeEach
    public void initTest() {
        typeTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTransfert() throws Exception {
        int databaseSizeBeforeCreate = typeTransfertRepository.findAll().size();
        // Create the TypeTransfert
        restTypeTransfertMockMvc.perform(post("/api/type-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTransfert)))
            .andExpect(status().isCreated());

        // Validate the TypeTransfert in the database
        List<TypeTransfert> typeTransfertList = typeTransfertRepository.findAll();
        assertThat(typeTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTransfert testTypeTransfert = typeTransfertList.get(typeTransfertList.size() - 1);
        assertThat(testTypeTransfert.getLibTypeTransfert()).isEqualTo(DEFAULT_LIB_TYPE_TRANSFERT);
    }

    @Test
    @Transactional
    public void createTypeTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTransfertRepository.findAll().size();

        // Create the TypeTransfert with an existing ID
        typeTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTransfertMockMvc.perform(post("/api/type-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTransfert in the database
        List<TypeTransfert> typeTransfertList = typeTransfertRepository.findAll();
        assertThat(typeTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeTransferts() throws Exception {
        // Initialize the database
        typeTransfertRepository.saveAndFlush(typeTransfert);

        // Get all the typeTransfertList
        restTypeTransfertMockMvc.perform(get("/api/type-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTypeTransfert").value(hasItem(DEFAULT_LIB_TYPE_TRANSFERT)));
    }
    
    @Test
    @Transactional
    public void getTypeTransfert() throws Exception {
        // Initialize the database
        typeTransfertRepository.saveAndFlush(typeTransfert);

        // Get the typeTransfert
        restTypeTransfertMockMvc.perform(get("/api/type-transferts/{id}", typeTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeTransfert.getId().intValue()))
            .andExpect(jsonPath("$.libTypeTransfert").value(DEFAULT_LIB_TYPE_TRANSFERT));
    }
    @Test
    @Transactional
    public void getNonExistingTypeTransfert() throws Exception {
        // Get the typeTransfert
        restTypeTransfertMockMvc.perform(get("/api/type-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTransfert() throws Exception {
        // Initialize the database
        typeTransfertRepository.saveAndFlush(typeTransfert);

        int databaseSizeBeforeUpdate = typeTransfertRepository.findAll().size();

        // Update the typeTransfert
        TypeTransfert updatedTypeTransfert = typeTransfertRepository.findById(typeTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTransfert are not directly saved in db
        em.detach(updatedTypeTransfert);
        updatedTypeTransfert
            .libTypeTransfert(UPDATED_LIB_TYPE_TRANSFERT);

        restTypeTransfertMockMvc.perform(put("/api/type-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeTransfert)))
            .andExpect(status().isOk());

        // Validate the TypeTransfert in the database
        List<TypeTransfert> typeTransfertList = typeTransfertRepository.findAll();
        assertThat(typeTransfertList).hasSize(databaseSizeBeforeUpdate);
        TypeTransfert testTypeTransfert = typeTransfertList.get(typeTransfertList.size() - 1);
        assertThat(testTypeTransfert.getLibTypeTransfert()).isEqualTo(UPDATED_LIB_TYPE_TRANSFERT);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTransfert() throws Exception {
        int databaseSizeBeforeUpdate = typeTransfertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTransfertMockMvc.perform(put("/api/type-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTransfert in the database
        List<TypeTransfert> typeTransfertList = typeTransfertRepository.findAll();
        assertThat(typeTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeTransfert() throws Exception {
        // Initialize the database
        typeTransfertRepository.saveAndFlush(typeTransfert);

        int databaseSizeBeforeDelete = typeTransfertRepository.findAll().size();

        // Delete the typeTransfert
        restTypeTransfertMockMvc.perform(delete("/api/type-transferts/{id}", typeTransfert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeTransfert> typeTransfertList = typeTransfertRepository.findAll();
        assertThat(typeTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
