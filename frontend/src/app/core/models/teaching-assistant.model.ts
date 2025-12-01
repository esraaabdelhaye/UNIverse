export interface TeachingAssistant {
  employeeId: string;
  fullName: string;
  email: string;
  role: 'TEACHING_ASSISTANT';
  phoneNumber: string;
  department: string;
  assignedCourses?: string[];
}
