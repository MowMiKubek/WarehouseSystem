package com.project.warehouse;

import com.project.warehouse.config.JwtService;
import com.project.warehouse.model.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class WarehouseIntegrationTests {
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
    public void noTokenProvided_whenGetWarehouses_thenStatus403() throws Exception {
        mockMvc.perform(get("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetWarehouses_thenStatus200() throws Exception {
        mockMvc.perform(get("/warehouse")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    public void givenValidId_whenGetWarehouse_thenStatus200() throws Exception {
        mockMvc.perform(get("/warehouse/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(jsonPath("$.quantity", is(1)))
                .andExpect(jsonPath("$.item.brand", is("Rimmel")))
                .andExpect(jsonPath("$.item.name", is("Szminka")))
                .andExpect(jsonPath("$.item.ean", is("1234567890123")));
    }

    @Test
    public void givenInvalidId_whenGetWarehouse_thenStatus404() throws Exception {
        mockMvc.perform(get("/warehouse/123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
