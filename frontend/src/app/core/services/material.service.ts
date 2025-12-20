import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Material } from '../models/material.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root',
})
export class MaterialService {
  constructor(private api: ApiService) {}

  getMaterialById(id: number): Observable<ApiResponse<Material>> {
    return this.api.get<ApiResponse<Material>>(`/api/materials/${id}`);
  }

  getMaterialsByCourse(courseId: number): Observable<ApiResponse<Material[]>> {
    return this.api.get<ApiResponse<Material[]>>(`/api/materials/course/${courseId}`);
  }

  getAllMaterials(): Observable<ApiResponse<Material[]>> {
    return this.api.get<ApiResponse<Material[]>>(`/api/materials`);
  }

  // uploadMaterial(courseId: number, material: Material): Observable<ApiResponse<Material>> {
  //   return this.api.post<ApiResponse<Material>>(`/api/materials/course/${courseId}`, material);
  // }
  uploadMaterial(courseId: number, formData: FormData) {
    return this.api.post<ApiResponse<Material>>(`/api/materials/course/${courseId}`, formData);
  }

  updateMaterial(id: number, material: Material): Observable<ApiResponse<Material>> {
    return this.api.put<ApiResponse<Material>>(`/api/materials/${id}`, material);
  }

  deleteMaterial(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/api/materials/${id}`);
  }

  downloadMaterial(url: string, fileName?: string): void {
    // Determine if it's a resource file or uploaded file
    const isResourceFile = url.startsWith('/materials/') || url.includes('Materials/');

    let downloadUrl: string;
    if (isResourceFile) {
      // For resource files, use the resources endpoint and clean the path
      const cleanPath = url.startsWith('/materials/')
        ? 'Materials/' + url.substring('/materials/'.length)
        : url;
      downloadUrl = `${this.api.getBaseUrl()}/api/files/resources?path=${encodeURIComponent(cleanPath)}`;
    } else {
      // For uploaded files, use the regular download endpoint with optional filename
      downloadUrl = `${this.api.getBaseUrl()}/api/files/download?path=${encodeURIComponent(url)}`;
      if (fileName) {
        downloadUrl += `&filename=${encodeURIComponent(fileName)}`;
      }
    }

    // Create temporary anchor and trigger download
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.target = '_blank';
    link.click();
  }

  viewMaterial(url: string, fileName?: string): void {
    // Determine if it's a resource file or uploaded file
    const isResourceFile = url.startsWith('/materials/') || url.includes('Materials/');

    let viewUrl: string;
    if (isResourceFile) {
      // For resource files, use the view-resource endpoint with inline disposition
      const cleanPath = url.startsWith('/materials/')
        ? 'Materials/' + url.substring('/materials/'.length)
        : url;
      viewUrl = `${this.api.getBaseUrl()}/api/files/view-resource?path=${encodeURIComponent(cleanPath)}`;
    } else {
      // For uploaded files, use the view endpoint with optional filename
      viewUrl = `${this.api.getBaseUrl()}/api/files/view?path=${encodeURIComponent(url)}`;
      if (fileName) {
        viewUrl += `&filename=${encodeURIComponent(fileName)}`;
      }
    }

    window.open(viewUrl, '_blank');
  }
}
