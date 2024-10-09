import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FieldOfStudyDto} from "../Dto/field-of-study-dto";

@Injectable({
  providedIn: 'root',
})
export class FieldOfStudyService {
  private apiUrl = 'http://localhost:8082/api/fields-of-study'; // Your API base URL

  constructor(private http: HttpClient) {}

  // Fetch all fields of study
  getAllFieldsOfStudy(): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(this.apiUrl);
  }

  // Fetch a field of study by ID
  getFieldOfStudyById(id: number): Observable<FieldOfStudyDto> {
    return this.http.get<FieldOfStudyDto>(`${this.apiUrl}/${id}`);
  }

  // Add a new field of study
  addFieldOfStudy(institutionId: number, fieldOfStudyDto: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    return this.http.post<FieldOfStudyDto>(`${this.apiUrl}/add/${institutionId}`, fieldOfStudyDto);
  }

  // Update a field of study
  updateFieldOfStudy(fosId: number, fieldOfStudyDto: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    let params = new HttpParams().set('fosId', fosId.toString());
    return this.http.put<FieldOfStudyDto>(`${this.apiUrl}/update`, fieldOfStudyDto, { params });
  }

  // Delete a field of study
  deleteFieldOfStudy(fieldOfStudyId: number): Observable<void> {
    let params = new HttpParams().set('fieldOfStudyId', fieldOfStudyId.toString());
    return this.http.delete<void>(`${this.apiUrl}/delete`, { params });
  }
}
