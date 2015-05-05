package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Appareil;
import fr.istic.sir.repository.AppareilRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppareilResource REST controller.
 *
 * @see AppareilResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AppareilResourceTest {

    private static final String DEFAULT_NOMAPP = "SAMPLE_TEXT";
    private static final String UPDATED_NOMAPP = "UPDATED_TEXT";
    private static final String DEFAULT_CONSO = "SAMPLE_TEXT";
    private static final String UPDATED_CONSO = "UPDATED_TEXT";

    @Inject
    private AppareilRepository appareilRepository;

    private MockMvc restAppareilMockMvc;

    private Appareil appareil;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppareilResource appareilResource = new AppareilResource();
        ReflectionTestUtils.setField(appareilResource, "appareilRepository", appareilRepository);
        this.restAppareilMockMvc = MockMvcBuilders.standaloneSetup(appareilResource).build();
    }

    @Before
    public void initTest() {
        appareil = new Appareil();
        appareil.setNomapp(DEFAULT_NOMAPP);
        appareil.setConso(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void createAppareil() throws Exception {
        // Validate the database is empty
        assertThat(appareilRepository.findAll()).hasSize(0);

        // Create the Appareil
        restAppareilMockMvc.perform(post("/api/appareils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appareil)))
                .andExpect(status().isCreated());

        // Validate the Appareil in the database
        List<Appareil> appareils = appareilRepository.findAll();
        assertThat(appareils).hasSize(1);
        Appareil testAppareil = appareils.iterator().next();
        assertThat(testAppareil.getNomapp()).isEqualTo(DEFAULT_NOMAPP);
        assertThat(testAppareil.getConso()).isEqualTo(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void getAllAppareils() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Get all the appareils
        restAppareilMockMvc.perform(get("/api/appareils"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(appareil.getId().intValue()))
                .andExpect(jsonPath("$.[0].nomapp").value(DEFAULT_NOMAPP.toString()))
                .andExpect(jsonPath("$.[0].conso").value(DEFAULT_CONSO.toString()));
    }

    @Test
    @Transactional
    public void getAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Get the appareil
        restAppareilMockMvc.perform(get("/api/appareils/{id}", appareil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(appareil.getId().intValue()))
            .andExpect(jsonPath("$.nomapp").value(DEFAULT_NOMAPP.toString()))
            .andExpect(jsonPath("$.conso").value(DEFAULT_CONSO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppareil() throws Exception {
        // Get the appareil
        restAppareilMockMvc.perform(get("/api/appareils/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Update the appareil
        appareil.setNomapp(UPDATED_NOMAPP);
        appareil.setConso(UPDATED_CONSO);
        restAppareilMockMvc.perform(put("/api/appareils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appareil)))
                .andExpect(status().isOk());

        // Validate the Appareil in the database
        List<Appareil> appareils = appareilRepository.findAll();
        assertThat(appareils).hasSize(1);
        Appareil testAppareil = appareils.iterator().next();
        assertThat(testAppareil.getNomapp()).isEqualTo(UPDATED_NOMAPP);
        assertThat(testAppareil.getConso()).isEqualTo(UPDATED_CONSO);
    }

    @Test
    @Transactional
    public void deleteAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Get the appareil
        restAppareilMockMvc.perform(delete("/api/appareils/{id}", appareil.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Appareil> appareils = appareilRepository.findAll();
        assertThat(appareils).hasSize(0);
    }
}
