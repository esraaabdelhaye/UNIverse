export interface Material {
  materialId: string;
  courseCode: string;
  materialTitle: string;
  materialType: 'LECTURE_SLIDES' | 'READING_MATERIAL' | 'PROBLEM_SET' | 'LAB_NOTES';
  fileSize: string;
  fileName: string;
  uploadDate: string;
  downloadUrl: string;
  description?: string;
  tags?: string[];
}
