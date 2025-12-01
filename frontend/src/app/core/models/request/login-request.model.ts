export interface LoginRequest {
  email: string;
  password: string;
  role: 'STUDENT' | 'TEACHING_STAFF' | 'SUPERVISOR';
}
