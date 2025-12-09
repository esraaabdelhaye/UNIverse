export interface Faculty {
  id: string;
  name: string;
  email: string;
  department: string;
  position: 'Professor' | 'Associate Professor' | 'Lecturer' | 'TA';
  phoneNumber?: string;
  officeLocation?: string;
  coursesTaught: number;
  status: 'active' | 'inactive' | 'on-leave';
  createdAt: Date;
  updatedAt: Date;
}
