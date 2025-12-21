import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { AskQuestionRequest, Question } from '../models/question.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import {ApiService} from './api.service';

@Injectable({ providedIn: 'root' })
export class QuestionService {
  private baseUrl = 'http://localhost:8080';

  constructor(private api: ApiService) {}

  askQuestion(request: AskQuestionRequest): Observable<ApiResponse<Question>> {
    return this.api.post(`/question/ask`, request);
  }

  updateQuestion(questionId: string, questionDTO: Partial<Question>): Observable<ApiResponse<Question>> {
    return this.api.post(`/question/update/${questionId}`, questionDTO);
  }

  deleteQuestion(questionId: string): Observable<ApiResponse<Question>> {
    return this.api.delete(`/question/${questionId}`)
  }

  getQuestion(questionId: string): Observable<ApiResponse<Question>> {
    return this.api.get(`/question/get/${questionId}`);
  }

  getAll(page = 0, pageSize = 20): Observable<ApiResponse<Question[]>> {
    return this.api.get(`/question/get/all?page=${page}&pageSize=${pageSize}`);
  }

  getByStudent(studentId: string | number, page = 0, pageSize = 50): Observable<ApiResponse<Question[]>> {
    return this.api.get(`/question/get/all/${studentId}?page=${page}&pageSize=${pageSize}`);
  }

  getByStatus(status: string): Observable<ApiResponse<Question[]>> {
    return this.api.get(`/question/get/all/status?status=${status}`);
  }

  getUnanswered(): Observable<ApiResponse<Question[]>> {
    return this.api.get(`/question/get/all/unanswered`);
  }

  search(keyword: string): Observable<ApiResponse<Question[]>> {
    return this.api.get(`/question/search?keyword=${encodeURIComponent(keyword)}`);
  }

  addUpvote(questionId: string): Observable<ApiResponse<Question>> {
    return this.api.post(`/question/addUpVote/${questionId}`, {});
  }

  incrementView(questionId: string): Observable<ApiResponse<Question>> {
    return this.api.post(`/question/incrementViewUpCount/${questionId}`, {});
  }
}
