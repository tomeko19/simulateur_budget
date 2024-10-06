import React, { useState, useEffect } from 'react';
import { Budget } from '../types';

interface BudgetFormProps {
  onSubmit: (budget: Partial<Budget>) => void;
  initialBudget?: Budget;
}

const BudgetForm: React.FC<BudgetFormProps> = ({ onSubmit, initialBudget }) => {
  const [totalAmount, setTotalAmount] = useState(initialBudget?.totalAmount || 0);
  const [remainingAmount, setRemainingAmount] = useState(initialBudget?.remainingAmount || 0);

  useEffect(() => {
    if (initialBudget) {
      setTotalAmount(initialBudget.totalAmount);
      setRemainingAmount(initialBudget.remainingAmount);
    }
  }, [initialBudget]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ totalAmount, remainingAmount });
    if (!initialBudget) {
      setTotalAmount(0);
      setRemainingAmount(0);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="totalAmount">
          Total Amount
        </label>
        <input
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="totalAmount"
          type="number"
          value={totalAmount}
          onChange={(e) => setTotalAmount(Number(e.target.value))}
          required
        />
      </div>
      <div className="mb-6">
        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="remainingAmount">
          Remaining Amount
        </label>
        <input
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="remainingAmount"
          type="number"
          value={remainingAmount}
          onChange={(e) => setRemainingAmount(Number(e.target.value))}
          required
        />
      </div>
      <div className="flex items-center justify-between">
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          {initialBudget ? 'Update Budget' : 'Create Budget'}
        </button>
      </div>
    </form>
  );
};

export default BudgetForm;