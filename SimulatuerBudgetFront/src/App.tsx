import React, { useState, useEffect } from 'react';
import { Budget } from './types';
import { getBudgetsByUserId, createBudget, updateBudget, deleteBudget } from './api';
import BudgetList from './components/BudgetList';
import BudgetForm from './components/BudgetForm';
import { Wallet } from 'lucide-react';

const App: React.FC = () => {
  const [budgets, setBudgets] = useState<Budget[]>([]);
  const [editingBudget, setEditingBudget] = useState<Budget | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchBudgets = async () => {
    try {
      setLoading(true);
      const response = await getBudgetsByUserId(1); // Assuming user ID 1 for this example
      if (response.status === 200) {
        setBudgets(response.data);
      } else {
        setError('Failed to fetch budgets');
      }
    } catch (err) {
      setError('An error occurred while fetching budgets');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBudgets();
  }, []);

  const handleCreateBudget = async (budget: Partial<Budget>) => {
    try {
      const response = await createBudget({ ...budget, userId: 1 } as Budget);
      if (response.status === 201) {
        setBudgets([...budgets, response.data]);
      } else {
        setError('Failed to create budget');
      }
    } catch (err) {
      setError('An error occurred while creating the budget');
    }
  };

  const handleUpdateBudget = async (budget: Partial<Budget>) => {
    if (!editingBudget) return;
    try {
      const response = await updateBudget(editingBudget.id, budget);
      if (response.status === 200) {
        setBudgets(budgets.map(b => b.id === editingBudget.id ? response.data : b));
        setEditingBudget(null);
      } else {
        setError('Failed to update budget');
      }
    } catch (err) {
      setError('An error occurred while updating the budget');
    }
  };

  const handleDeleteBudget = async (id: number) => {
    try {
      const response = await deleteBudget(id);
      if (response.status === 204) {
        setBudgets(budgets.filter(b => b.id !== id));
      } else {
        setError('Failed to delete budget');
      }
    } catch (err) {
      setError('An error occurred while deleting the budget');
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-blue-400 to-purple-500 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-3xl mx-auto">
        <div className="text-center mb-8">
          <Wallet className="mx-auto h-12 w-12 text-white" />
          <h1 className="mt-2 text-4xl font-extrabold text-white">Budget Management</h1>
        </div>
        
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
            <span className="block sm:inline">{error}</span>
          </div>
        )}
        
        <div className="bg-white shadow-xl rounded-lg overflow-hidden">
          <div className="p-6">
            <h2 className="text-2xl font-bold text-gray-800 mb-4">
              {editingBudget ? 'Edit Budget' : 'Create New Budget'}
            </h2>
            <BudgetForm
              onSubmit={editingBudget ? handleUpdateBudget : handleCreateBudget}
              initialBudget={editingBudget || undefined}
            />
          </div>
        </div>
        
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-white mb-4">Your Budgets</h2>
          {loading ? (
            <p className="text-white">Loading budgets...</p>
          ) : (
            <BudgetList
              budgets={budgets}
              onEdit={setEditingBudget}
              onDelete={handleDeleteBudget}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default App;