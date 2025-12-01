export interface Course {
  courseCode: string;
  courseTitle: string;
  department: string;
  instructorId: string;
  instructorName: string;
  courseDescription?: string;
  capacity: number;
  enrolled: number;
  credits: number;
  semester: string;
  status: 'OPEN' | 'FULL' | 'CLOSED';
  createdDate: string;
  updatedDate: string;
}

