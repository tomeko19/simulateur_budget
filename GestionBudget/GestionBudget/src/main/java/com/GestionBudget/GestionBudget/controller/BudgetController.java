package com.GestionBudget.GestionBudget.controller;

import com.GestionBudget.GestionBudget.model.Budget;
import com.GestionBudget.GestionBudget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budgets")
@CrossOrigin(origins = "http://localhost:5173")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        // Appel au service pour créer le budget
        Budget createdBudget = budgetService.createBudget(budget);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id){
        Optional<Budget> budget = budgetService.getBudgetById(id);
        return budget.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getBudgetByUserId(@PathVariable Long userId){
        List<Budget> budget = budgetService.getAllBudgetsByUser(userId);
        if(budget.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(budget,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        if(budgetService.getBudgetById(id)==null){
            return ResponseEntity.notFound().build();
        }
        budgetService.deleteBudget(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
     Optional<Budget> existingBudget = budgetService.getBudgetById(id);
     if(existingBudget.isPresent()){
        Budget updatedBudget =  existingBudget.get();
        updatedBudget.setUserId(budget.getUserId());
        updatedBudget.setRemainingAmount(budget.getRemainingAmount());
        updatedBudget.setTotalAmount(budget.getTotalAmount());
        updatedBudget.setCreatedAt( new Date());
         budgetService.updateBudget(id,updatedBudget);
         return  new ResponseEntity<>(updatedBudget,HttpStatus.OK);
     }
     else{
         return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

    }

}
