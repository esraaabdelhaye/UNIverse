export interface FacultyMember {
  id: string;
  name: string;
  email: string;
  department?: string;
  role?: string;
  imageUrl?: string;
  position?: string;
  status?: 'Active' | 'On Leave' | 'Inactive';
}

