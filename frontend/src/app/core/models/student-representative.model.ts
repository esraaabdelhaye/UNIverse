export interface StudentRepresentative {
  representativeId: string;
  studentId: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  department: string;
  section: string;
  semester: string;
  appointmentDate: string;
  endDate?: string;
  status: 'ACTIVE' | 'INACTIVE';
  constituentsCount: number;
}
