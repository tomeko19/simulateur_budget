package com.GestionBudget.GestionBudget.service;

import com.GestionBudget.GestionBudget.model.Budget;
import com.GestionBudget.GestionBudget.repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
     @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public Budget createBudget(Budget budget){
       if(budget.getUserId() == null){
           throw new IllegalArgumentException("l'id de l'utilisateur ne peut pas etre null");
       }
       if(budget.getTotalAmount() == null || budget.getRemainingAmount() <=0){
           throw new IllegalArgumentException("Le montant total doit etre superieur a zero.");
       }
       return budgetRepository.save(budget);
    }

    /* mettre a jour un budget existant*/
    public Budget updateBudget(Long id, Budget budget){
        Optional<Budget> existingBudgetOptional = budgetRepository.findById(id);
        if (!existingBudgetOptional.isPresent()) {
            throw new EntityNotFoundException("Budget avec l'ID " + id + " non trouvé.");
        }
        // Récupérer le budget existant
        Budget existingBudget = existingBudgetOptional.get();

        //mettre a jour les champs du budget existant
        if(budget.getUserId() !=null){
            existingBudget.setUserId(budget.getUserId());
        }
        if(budget.getTotalAmount()!=null){
            existingBudget.setTotalAmount(budget.getTotalAmount());
        }
        if(budget.getRemainingAmount()!=null){
            existingBudget.setRemainingAmount(budget.getRemainingAmount());
        }
        //ici nous mettons a jour la date
        existingBudget.setUpdatedAt(new Date());
        // enregistrons le budget modifier dans la repository
        return budgetRepository.save(existingBudget);
    }

    public void deleteBudget(Long id){
        if(id == null){
            throw  new IllegalArgumentException("L'id a supprimé n'est pas presente dans la base de donnée ");
        }
        budgetRepository.deleteById(id);
    }
    //Pour récupérer tous les budgets d’un utilisateur.
    public List<Budget> getAllBudgetsByUser(Long userId){
        if(userId == null){
            throw  new IllegalArgumentException("L'id de l'utilisateur n'est pas presente dans la base de donnée ");
        }
      return  budgetRepository.findByUserId(userId);
    }

    public Optional<Budget> getBudgetById(Long id){
        if(id==null){
            throw  new IllegalArgumentException(" userId non può essere impostato a nullo");
        }
        return budgetRepository.findById(id);
    }
}
