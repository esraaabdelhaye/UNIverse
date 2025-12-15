export interface RegisterStudentRequest {
  fullName: string;
  email?: string;
  password?: string;
  studentId?: string;
  dateOfBirth?: string;
  phone?: string;
  academicId?: string;
}
