export interface AuthResponse {
  token: string;
  userId: string;
  fullName: string;
  role: string;
  email: string;
  expiresAt: string;
}
