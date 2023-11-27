package com.project.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.warehouse.config.JwtService;
import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.CreateDocumentDTO;
import com.project.warehouse.model.dto.CreateDocumentLineDTO;
import com.project.warehouse.model.enums.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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
public class DocumentIntegrationTests {
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
    public void noTokenProvided_whenGetDocuments_thenStatus403() throws Exception {
        mockMvc.perform(get("/documents")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetDocuments_thenStatus200() throws Exception {
        mockMvc.perform(get("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));

    }

    @Test
    public void givenValidId_whenGetDocument_thenStatus200() throws Exception {
        mockMvc.perform(get("/documents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(jsonPath("$.sender", is("Kosmetix")))
                .andExpect(jsonPath("$.documentLines", hasSize(3)));
    }

    @Test
    public void givenInvalidId_whenGetDocument_thenStatus404() throws Exception {
        mockMvc.perform(get("/documents/31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenValidInput_createDocument_thenStatus201() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CreateDocumentLineDTO> lines = new ArrayList<>();
        lines.add(new CreateDocumentLineDTO(3, 1));
        lines.add(new CreateDocumentLineDTO(4, 7));

        CreateDocumentDTO documentDTO =
                new CreateDocumentDTO("123", "Jan Nowak", "Stacho Jones", DocumentType.PZ, lines);
        mockMvc.perform(post("/documents")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(documentDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nr", is("123")))
                .andExpect(jsonPath("$.sender", is("Jan Nowak")))
                .andExpect(jsonPath("$.client", is("Stacho Jones")))
                .andExpect(jsonPath("$.type", is("PZ")));
    }
}
