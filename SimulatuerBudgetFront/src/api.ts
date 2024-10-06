import { Budget, ApiResponse } from './types';

const API_URL = 'http://localhost:8081/api/budgets';

export const getBudgetsByUserId = async (userId: number): Promise<ApiResponse<Budget[]>> => {
  const response = await fetch(`${API_URL}/user/${userId}`);
  const data = await response.json();
  return { data, status: response.status };
};

export const createBudget = async (budget: Omit<Budget, 'id' | 'createdAt'>): Promise<ApiResponse<Budget>> => {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(budget),
  });
  const data = await response.json();
  return { data, status: response.status };
};

export const updateBudget = async (id: number, budget: Partial<Budget>): Promise<ApiResponse<Budget>> => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(budget),
  });
  const data = await response.json();
  return { data, status: response.status };
};

export const deleteBudget = async (id: number): Promise<ApiResponse<void>> => {
  const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
  return { data: undefined, status: response.status };
};