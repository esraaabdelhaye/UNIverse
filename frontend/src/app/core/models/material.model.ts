export interface Material {
  id: number;
  title: string;
  type: 'TEXTBOOK' | 'PDF' | 'VIDEO' | 'RECORDING' | 'OTHER';
  url: string;
  uploadDate: string;
  courseCode: string;
  courseName?: string;
  uploaderName?: string;
}
