export interface Budget {
  id?: number;
  userId: number;
  totalAmount: number;
  remainingAmount: number;
  createdAt?: string;
}

export interface ApiResponse<T> {
  data: T;
  status: number;
}
