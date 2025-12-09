export interface User {
  id: string;
  email: string;
  name: string;
  role: 'student' | 'professor' | 'admin' | 'ta';
  profilePicture?: string;
  department?: string;
  phoneNumber?: string;
  createdAt: Date;
  updatedAt: Date;
}
