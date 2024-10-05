import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FieldOfStudy} from "../model/field-of-study";
import {FieldOfStudyDto} from "../Dto/field-of-study-dto";

@Injectable({
  providedIn: 'root',
})
export class FieldOfStudyService {
  private baseUrl = 'http://localhost:8089/api/fields-of-study';

  constructor(private http: HttpClient) {}

  getAllFieldsOfStudy(): Observable<FieldOfStudy[]> {
    return this.http.get<FieldOfStudy[]>(`${this.baseUrl}`);
  }

  addFieldOfStudy(fieldOfStudyDto: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    return this.http.post<FieldOfStudyDto>(`${this.baseUrl}/add`, fieldOfStudyDto);
  }

  updateFieldOfStudy(fosId: number, fieldOfStudy: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    return this.http.put<FieldOfStudyDto>(`${this.baseUrl}/update?fosId=${fosId}`, fieldOfStudy);
  }

  deleteFieldOfStudy(fieldOfStudyId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete?fieldOfStudyId=${fieldOfStudyId}`);
  }

  getRecommendations(studentId: number): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/${studentId}/recommendations`);
  }
}
