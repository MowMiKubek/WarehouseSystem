package com.project.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.warehouse.config.JwtService;
import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.CreateItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ItemIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private String jwtToken;

    @BeforeEach
    public void setup() {
        jwtToken = "Bearer " + jwtService.generateToken(new User(null, "Maciej", "Maruda", "mmaruda", "haslo123"));
    }

    @Test
    public void noTokenProvided_whenGetItems_thenStatus403() throws Exception {
        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetItems_thenStatus200() throws Exception {
        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(8))));
    }

    @Test
    public void givenValidId_whenGetItem_thenStatus200() throws Exception {
        mockMvc.perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Rimmel")))
                .andExpect(jsonPath("$.name", is("Szminka")))
                .andExpect(jsonPath("$.ean", is("1234567890123")));
    }

    @Test
    public void givenInvalidId_whenGetItem_thenStatus200() throws Exception {
        mockMvc.perform(get("/items/" + Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidEan_whenGetItemsByEan_thenStatus200() throws Exception {
        mockMvc.perform(get("/items/ean/1234567890123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Rimmel")))
                .andExpect(jsonPath("$.name", is("Szminka")))
                .andExpect(jsonPath("$.ean", is("1234567890123")));
    }

    @Test
    public void givenInvalidEan_whenGetItem_thenStatus404() throws Exception {
        mockMvc.perform(get("/items/ean/invalidEan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenItemData_whenCreateItem_thenStatus201() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateItemDTO createItemDTO = new CreateItemDTO("Rimmel", "Pomadka", "2345678");
        mockMvc.perform(post("/items")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createItemDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand", is("Rimmel")))
                .andExpect(jsonPath("$.name", is("Pomadka")))
                .andExpect(jsonPath("$.ean", is("2345678")));
    }
}
