export interface Course {
  id: string;
  code: string;
  title: string;
  description: string;
  department: string;
  instructorId: string;
  instructorName: string;
  capacity: number;
  enrolled: number;
  status: 'open' | 'full' | 'closed';
  credits: number;
  schedule: string;
  createdAt: Date;
  updatedAt: Date;
}
