package com.GestionBudget.GestionBudget.controller;

import com.GestionBudget.GestionBudget.model.Budget;
import com.GestionBudget.GestionBudget.service.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BudgetService budgetService; // on mocke le service pour ne pas utiliser la vrai couche bussness
    @Autowired
    private ObjectMapper objectMapper; // convertir les objets en JSON

    @Test
    public void testCreateBudget() throws Exception {
        // Données de test
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setUserId(123L);
        budget.setTotalAmount(5000.0);
        budget.setRemainingAmount(2000.0);

        // On simule la réponse du service
        Mockito.when(budgetService.createBudget(Mockito.any(Budget.class))).thenReturn(budget);

        // On convertit l'objet budget en JSON
        String budgetJson = objectMapper.writeValueAsString(budget);

        // Requête POST
        mockMvc.perform(post("/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(budgetJson)) // On envoie les données en JSON
                .andExpect(status().isCreated()) // On vérifie que le status HTTP est 201
                .andExpect( jsonPath("$.id").value(1L)) // On vérifie que l'ID renvoyé est correct
                .andExpect(jsonPath("$.userId").value(123L))
                .andExpect(jsonPath("$.totalAmount").value(5000.0))
                .andExpect(jsonPath("$.remainingAmount").value(2000.0));
    }

}
