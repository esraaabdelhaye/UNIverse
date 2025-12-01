export interface User {
  userId: string;
  fullName: string;
  email: string;
  role: 'STUDENT' | 'TEACHER' | 'SUPERVISOR' | 'DOCTOR' | 'STUDENT_REP' | 'TEACHING_ASSISTANT';
  phoneNumber?: string;
  profileImage?: string;
  createdDate: string;
  updatedDate: string;
  isActive: boolean;
}
