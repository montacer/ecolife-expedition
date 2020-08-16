package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;
import ch.itsforward.ecolifeexpedition.repository.TypeCircuitRepository;

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
 * Integration tests for the {@link TypeCircuitResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeCircuitResourceIT {

    private static final String DEFAULT_LIB_TYPE_CIRCUIT = "AAAAAAAAAA";
    private static final String UPDATED_LIB_TYPE_CIRCUIT = "BBBBBBBBBB";

    @Autowired
    private TypeCircuitRepository typeCircuitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeCircuitMockMvc;

    private TypeCircuit typeCircuit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCircuit createEntity(EntityManager em) {
        TypeCircuit typeCircuit = new TypeCircuit()
            .libTypeCircuit(DEFAULT_LIB_TYPE_CIRCUIT);
        return typeCircuit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCircuit createUpdatedEntity(EntityManager em) {
        TypeCircuit typeCircuit = new TypeCircuit()
            .libTypeCircuit(UPDATED_LIB_TYPE_CIRCUIT);
        return typeCircuit;
    }

    @BeforeEach
    public void initTest() {
        typeCircuit = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCircuit() throws Exception {
        int databaseSizeBeforeCreate = typeCircuitRepository.findAll().size();
        // Create the TypeCircuit
        restTypeCircuitMockMvc.perform(post("/api/type-circuits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCircuit)))
            .andExpect(status().isCreated());

        // Validate the TypeCircuit in the database
        List<TypeCircuit> typeCircuitList = typeCircuitRepository.findAll();
        assertThat(typeCircuitList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCircuit testTypeCircuit = typeCircuitList.get(typeCircuitList.size() - 1);
        assertThat(testTypeCircuit.getLibTypeCircuit()).isEqualTo(DEFAULT_LIB_TYPE_CIRCUIT);
    }

    @Test
    @Transactional
    public void createTypeCircuitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCircuitRepository.findAll().size();

        // Create the TypeCircuit with an existing ID
        typeCircuit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCircuitMockMvc.perform(post("/api/type-circuits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCircuit)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCircuit in the database
        List<TypeCircuit> typeCircuitList = typeCircuitRepository.findAll();
        assertThat(typeCircuitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeCircuits() throws Exception {
        // Initialize the database
        typeCircuitRepository.saveAndFlush(typeCircuit);

        // Get all the typeCircuitList
        restTypeCircuitMockMvc.perform(get("/api/type-circuits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCircuit.getId().intValue())))
            .andExpect(jsonPath("$.[*].libTypeCircuit").value(hasItem(DEFAULT_LIB_TYPE_CIRCUIT)));
    }
    
    @Test
    @Transactional
    public void getTypeCircuit() throws Exception {
        // Initialize the database
        typeCircuitRepository.saveAndFlush(typeCircuit);

        // Get the typeCircuit
        restTypeCircuitMockMvc.perform(get("/api/type-circuits/{id}", typeCircuit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeCircuit.getId().intValue()))
            .andExpect(jsonPath("$.libTypeCircuit").value(DEFAULT_LIB_TYPE_CIRCUIT));
    }
    @Test
    @Transactional
    public void getNonExistingTypeCircuit() throws Exception {
        // Get the typeCircuit
        restTypeCircuitMockMvc.perform(get("/api/type-circuits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCircuit() throws Exception {
        // Initialize the database
        typeCircuitRepository.saveAndFlush(typeCircuit);

        int databaseSizeBeforeUpdate = typeCircuitRepository.findAll().size();

        // Update the typeCircuit
        TypeCircuit updatedTypeCircuit = typeCircuitRepository.findById(typeCircuit.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCircuit are not directly saved in db
        em.detach(updatedTypeCircuit);
        updatedTypeCircuit
            .libTypeCircuit(UPDATED_LIB_TYPE_CIRCUIT);

        restTypeCircuitMockMvc.perform(put("/api/type-circuits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeCircuit)))
            .andExpect(status().isOk());

        // Validate the TypeCircuit in the database
        List<TypeCircuit> typeCircuitList = typeCircuitRepository.findAll();
        assertThat(typeCircuitList).hasSize(databaseSizeBeforeUpdate);
        TypeCircuit testTypeCircuit = typeCircuitList.get(typeCircuitList.size() - 1);
        assertThat(testTypeCircuit.getLibTypeCircuit()).isEqualTo(UPDATED_LIB_TYPE_CIRCUIT);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCircuit() throws Exception {
        int databaseSizeBeforeUpdate = typeCircuitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCircuitMockMvc.perform(put("/api/type-circuits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCircuit)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCircuit in the database
        List<TypeCircuit> typeCircuitList = typeCircuitRepository.findAll();
        assertThat(typeCircuitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCircuit() throws Exception {
        // Initialize the database
        typeCircuitRepository.saveAndFlush(typeCircuit);

        int databaseSizeBeforeDelete = typeCircuitRepository.findAll().size();

        // Delete the typeCircuit
        restTypeCircuitMockMvc.perform(delete("/api/type-circuits/{id}", typeCircuit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeCircuit> typeCircuitList = typeCircuitRepository.findAll();
        assertThat(typeCircuitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
