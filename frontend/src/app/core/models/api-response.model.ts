export interface ApiResponse<T> {
  statusCode: number;
  message: string;
  data?: T;
  success: boolean;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
  pageSize: number;
}
