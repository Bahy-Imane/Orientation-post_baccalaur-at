import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BacType } from '../enums/bac-type';
import {FieldOfStudyDto} from "../Dto/field-of-study-dto";

@Injectable({
  providedIn: 'root'
})
export class FieldOfStudyService {
  private apiUrl = 'http://localhost:8080/api/fields-of-study';

  constructor(private http: HttpClient) { }

  getAllFieldsOfStudy(): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.apiUrl}/all-fields`);
  }

  getFieldOfStudyById(id: number): Observable<FieldOfStudyDto> {
    return this.http.get<FieldOfStudyDto>(`${this.apiUrl}/${id}`);
  }

  addFieldOfStudy(fieldOfStudyDto: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    return this.http.post<FieldOfStudyDto>(`${this.apiUrl}/add-field-of-study`, fieldOfStudyDto);
  }

  updateFieldOfStudy(fosId: number, fieldOfStudyDto: FieldOfStudyDto): Observable<FieldOfStudyDto> {
    return this.http.put<FieldOfStudyDto>(`${this.apiUrl}/update?fosId=${fosId}`, fieldOfStudyDto);
  }

  deleteFieldOfStudy(fieldOfStudyId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete?fieldOfStudyId=${fieldOfStudyId}`);
  }

  getRecommendations(): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.apiUrl}/recommendation`);
  }

  filterAndSearchFields(bacTypeRequired?: BacType, minimumBacNote?: number, searchText: string = ''): Observable<FieldOfStudyDto[]> {
    let params = new HttpParams();
    if (bacTypeRequired) {
      params = params.set('bacTypeRequired', bacTypeRequired);
    }
    if (minimumBacNote) {
      params = params.set('minimumBacNote', minimumBacNote.toString());
    }
    params = params.set('searchText', searchText);

    return this.http.get<FieldOfStudyDto[]>(`${this.apiUrl}/filter-fields`, { params });
  }
}
