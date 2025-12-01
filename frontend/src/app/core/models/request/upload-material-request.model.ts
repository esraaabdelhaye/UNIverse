export interface UploadMaterialRequest {
  courseCode: string;
  materialTitle: string;
  materialType: string;
  description: string;
  tags?: string[];
  fileContent: File;
  fileName: string;
}
