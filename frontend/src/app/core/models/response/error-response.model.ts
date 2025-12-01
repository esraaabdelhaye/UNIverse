export interface ErrorResponse {
  statusCode: number;
  message: string;
  errorCode: string;
  details?: string;
  timestamp: string;
  path?: string;
}
