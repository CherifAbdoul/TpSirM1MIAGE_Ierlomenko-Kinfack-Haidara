package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Chauffage;
import fr.istic.sir.repository.ChauffageRepository;

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
 * Test class for the ChauffageResource REST controller.
 *
 * @see ChauffageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ChauffageResourceTest {


    @Inject
    private ChauffageRepository chauffageRepository;

    private MockMvc restChauffageMockMvc;

    private Chauffage chauffage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChauffageResource chauffageResource = new ChauffageResource();
        ReflectionTestUtils.setField(chauffageResource, "chauffageRepository", chauffageRepository);
        this.restChauffageMockMvc = MockMvcBuilders.standaloneSetup(chauffageResource).build();
    }

    @Before
    public void initTest() {
        chauffage = new Chauffage();
    }

    @Test
    @Transactional
    public void createChauffage() throws Exception {
        // Validate the database is empty
        assertThat(chauffageRepository.findAll()).hasSize(0);

        // Create the Chauffage
        restChauffageMockMvc.perform(post("/api/chauffages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chauffage)))
                .andExpect(status().isCreated());

        // Validate the Chauffage in the database
        List<Chauffage> chauffages = chauffageRepository.findAll();
        assertThat(chauffages).hasSize(1);
        Chauffage testChauffage = chauffages.iterator().next();
    }

    @Test
    @Transactional
    public void getAllChauffages() throws Exception {
        // Initialize the database
        chauffageRepository.saveAndFlush(chauffage);

        // Get all the chauffages
        restChauffageMockMvc.perform(get("/api/chauffages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(chauffage.getId().intValue()));
    }

    @Test
    @Transactional
    public void getChauffage() throws Exception {
        // Initialize the database
        chauffageRepository.saveAndFlush(chauffage);

        // Get the chauffage
        restChauffageMockMvc.perform(get("/api/chauffages/{id}", chauffage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(chauffage.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChauffage() throws Exception {
        // Get the chauffage
        restChauffageMockMvc.perform(get("/api/chauffages/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChauffage() throws Exception {
        // Initialize the database
        chauffageRepository.saveAndFlush(chauffage);

        // Update the chauffage
        restChauffageMockMvc.perform(put("/api/chauffages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chauffage)))
                .andExpect(status().isOk());

        // Validate the Chauffage in the database
        List<Chauffage> chauffages = chauffageRepository.findAll();
        assertThat(chauffages).hasSize(1);
        Chauffage testChauffage = chauffages.iterator().next();
    }

    @Test
    @Transactional
    public void deleteChauffage() throws Exception {
        // Initialize the database
        chauffageRepository.saveAndFlush(chauffage);

        // Get the chauffage
        restChauffageMockMvc.perform(delete("/api/chauffages/{id}", chauffage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Chauffage> chauffages = chauffageRepository.findAll();
        assertThat(chauffages).hasSize(0);
    }
}
