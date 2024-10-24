package com.spacemedia.spaceship.controller;

import com.spacemedia.spaceship.entity.Spaceship;
import com.spacemedia.spaceship.repository.SpaceshipRepository;
import lombok.With;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpaceshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @BeforeEach
    public void testInitialDataLoad() throws Exception {
        spaceshipRepository.deleteAll();

        Spaceship spaceship1 = new Spaceship("Millennium Falcon");
        Spaceship spaceship2 = new Spaceship("USS Enterprise");

        spaceshipRepository.save(spaceship1);
        spaceshipRepository.save(spaceship2);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getAllSpaceships() throws Exception{
        mockMvc.perform(get("/spaceships?page=0&size=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value("Millennium Falcon"))
                .andExpect(jsonPath("$.content[1].name").value("USS Enterprise"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getSpaceshipById() throws Exception{
        mockMvc.perform(get("/spaceships/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Millennium Falcon"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getSpaceshipByPartialName() throws Exception{
        String spaceshipName = "mil";
        mockMvc.perform(get("/spaceships/search?name="+spaceshipName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Millennium Falcon"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void createSpaceship() throws Exception {
        String spaceshipJson = "{\"name\": \"Serenity\"}";

        mockMvc.perform(post("/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(spaceshipJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Serenity"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void editSpaceship() throws Exception {
        // Verificar que la nave tiene el nombre
        Assertions.assertTrue(spaceshipRepository.findByName("Millennium Falcon").getFirst().getName().equals("Millennium Falcon"));

        // Modificamos el nombre
        String spaceshipJson = "{\"id\": \"1\",\"name\": \"Millennium Edited\"}";

        mockMvc.perform(post("/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(spaceshipJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Millennium Edited"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void deleteSpaceship() throws Exception {
        //creamos una nueva nave
        Spaceship spaceship = new Spaceship("Santisima trinidad");
        spaceshipRepository.save(spaceship);

        Long id = spaceship.getId();

        mockMvc.perform(delete("/spaceships/{id}", id))
                .andExpect(status().isNoContent());

        // Verificar que la nave fue eliminada
        Assertions.assertFalse(spaceshipRepository.findById(id).isPresent());
    }
}