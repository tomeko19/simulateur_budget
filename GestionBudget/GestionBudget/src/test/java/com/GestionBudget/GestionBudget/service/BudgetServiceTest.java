package com.GestionBudget.GestionBudget.service;

import com.GestionBudget.GestionBudget.model.Budget;
import com.GestionBudget.GestionBudget.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BudgetServiceTest {
    @InjectMocks
    private BudgetService budgetService;
    @Mock
    private BudgetRepository budgetRepository;

    @BeforeEach
    public  void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateBudget_validBudget(){
        Budget budget = new Budget();
        budget.setUserId(1L);
        budget.setTotalAmount(100.0);
        budget.setRemainingAmount(100.0);

        when(budgetRepository.save(budget)).thenReturn(budget);

        //ACT
        Budget createdBudget = budgetService.createBudget(budget);
        // Assert
        assertNotNull(createdBudget);
        assertEquals(budget.getUserId(),createdBudget.getUserId());
        verify(budgetRepository, times(1)).save(budget);
    }

    @Test
    public void testCreateBudget_UserIdNull() {
        // Arrange
        Budget budget = new Budget();
        budget.setUserId(null);
        budget.setTotalAmount(100.0);
        budget.setRemainingAmount(100.0);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            budgetService.createBudget(budget);
        });

        assertEquals("l'id de l'utilisateur ne peut pas etre null", exception.getMessage());
    }
    @Test
    public void testUpdateBudget(){
       Long budgetId = 1L;
       Budget existingBudget = new Budget();
       existingBudget.setId(budgetId);
       existingBudget.setUserId(1L);
       existingBudget.setTotalAmount(100.0);
       existingBudget.setRemainingAmount(50.0);

       Budget updatedBudget = new Budget();
       updatedBudget.setUserId(1L);
       updatedBudget.setTotalAmount(150.0);
       updatedBudget.setRemainingAmount(75.0);

        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(existingBudget));
        when(budgetRepository.save(any(Budget.class))).thenReturn(updatedBudget);

        // Act
        Budget result = budgetService.updateBudget(budgetId, updatedBudget);

        // Assert
        assertNotNull(result);
        assertEquals(updatedBudget.getTotalAmount(), result.getTotalAmount());
        assertEquals(updatedBudget.getRemainingAmount(), result.getRemainingAmount());

        // Verify that the repository methods were called
        verify(budgetRepository, times(1)).findById(budgetId);
        verify(budgetRepository, times(1)).save(existingBudget);
    }
    @Test
    public void testfindById(){
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setUserId(2L);
        budget.setTotalAmount(100.0);
        budget.setRemainingAmount(50.0);
        budget.setCreatedAt(new Date());

        when(budgetRepository.findById(budget.getId())).thenReturn(Optional.of(budget));

       Optional<Budget>  budget1 = budgetService.getBudgetById(budget.getId());
       assertTrue(!budget1.isEmpty());
    }
}
