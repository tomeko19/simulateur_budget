package com.GestionBudget.GestionBudget.repository;

import com.GestionBudget.GestionBudget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // Méthode pour récupérer tous les budgets associés à un utilisateur spécifique
    List<Budget> findByUserId(Long userId);

    // Méthode pour récupérer un budget par son identifiant
    Optional<Budget> findById(Long id);
    //Methode pour supprimer un element dans la base de donnée
    void deleteById(Long id);

}
