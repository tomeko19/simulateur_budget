import React from 'react';
import { Budget } from '../types';
import { Trash2, Edit } from 'lucide-react';

interface BudgetListProps {
  budgets: Budget[];
  onEdit: (budget: Budget) => void;
  onDelete: (id: number) => void;
}

const BudgetList: React.FC<BudgetListProps> = ({ budgets, onEdit, onDelete }) => {
  return (
    <div className="bg-white shadow-md rounded-lg overflow-hidden">
      <table className="w-full">
        <thead className="bg-blue-500 text-white">
          <tr>
            <th className="px-4 py-2">Total Amount</th>
            <th className="px-4 py-2">Remaining Amount</th>
            <th className="px-4 py-2">Created At</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {budgets.map((budget) => (
            <tr key={budget.id} className="border-b hover:bg-gray-100">
              <td className="px-4 py-2">${budget.totalAmount.toFixed(2)}</td>
              <td className="px-4 py-2">${budget.remainingAmount.toFixed(2)}</td>
              <td className="px-4 py-2">{budget.createdAt ? new Date(budget.createdAt).toLocaleDateString() : 'N/A'}</td>

              <td className="px-4 py-2">
                <button
                  onClick={() => onEdit(budget)}
                  className="text-blue-500 hover:text-blue-700 mr-2"
                >
                  <Edit size={18} />
                </button>
                <button
                  onClick={() => onDelete(budget.id)}
                  className="text-red-500 hover:text-red-700"
                >
                  <Trash2 size={18} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BudgetList;
