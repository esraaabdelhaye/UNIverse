export interface Student {
  studentId: string;
  fullName: string;
  email: string;
  dateOfBirth: string;
  phoneNumber: string;
  enrollmentStatus: 'ACTIVE' | 'INACTIVE' | 'GRADUATED';
}
