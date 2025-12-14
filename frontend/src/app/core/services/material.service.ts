import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Material } from '../models/material.model';
import {ApiResponse} from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
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

  uploadMaterial(courseId: number, material: Material): Observable<ApiResponse<Material>> {
    return this.api.post<ApiResponse<Material>>(`/api/materials/course/${courseId}`, material);
  }

  updateMaterial(id: number, material: Material): Observable<ApiResponse<Material>> {
    return this.api.put<ApiResponse<Material>>(`/api/materials/${id}`, material);
  }

  deleteMaterial(id: number): Observable<ApiResponse<void>> {
    return this.api.delete<ApiResponse<void>>(`/api/materials/${id}`);
  }
}
