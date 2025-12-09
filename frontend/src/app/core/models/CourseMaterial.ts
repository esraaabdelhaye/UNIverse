export interface CourseMaterial {
  id: string;
  courseId: string;
  title: string;
  type: 'lecture' | 'reading' | 'problem' | 'syllabus' | 'other';
  description?: string;
  fileName: string;
  filePath: string;
  fileSize: number;
  uploadedBy: string;
  uploadedAt: Date;
  updatedAt: Date;
}
