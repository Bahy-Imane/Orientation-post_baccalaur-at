import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FieldOfStudyDto} from "../Dto/field-of-study-dto";
import {BacType} from "../enums/bac-type";


@Injectable({
  providedIn: 'root'
})
export class FieldOfStudyService {

  private baseUrl = 'http://localhost:8081/api/fields-of-study';

  constructor(private http: HttpClient) {}

  getAllFieldsOfStudy(): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/all-fields`);
  }

  getFieldOfStudiesByInstitutionId(institutionId: number): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/institution/${institutionId}`);
  }

  getFieldOfStudyById(fosId: number): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/${fosId}`);
  }

  addFieldOfStudy(fieldOfStudyDto: FieldOfStudyDto): Observable<{ msg: string }> {
    return this.http.post<{ msg: string }>(`${this.baseUrl}/add-field-of-study`, fieldOfStudyDto);
  }

  updateFieldOfStudy(fosId: number, fieldOfStudyDto: FieldOfStudyDto): Observable<{ msg: string }> {
    return this.http.put<{ msg: string }>(`${this.baseUrl}/update/${fosId}`, fieldOfStudyDto);
  }

  deleteFieldOfStudy(fosId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${fosId}`);
  }

  getRecommendation(): Observable<FieldOfStudyDto[]> {
    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/recommendation`);
  }

  searchFieldsOfStudy(searchText?: string): Observable<FieldOfStudyDto[]> {
    let params = new HttpParams();
    if (searchText) {
      params = params.set('searchText', searchText);
    }

    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/search`, { params });
  }

  filterFieldsOfStudyByBacType(bacTypeRequired?: BacType | null): Observable<FieldOfStudyDto[]> {
    let params = new HttpParams();
    if (bacTypeRequired) {
      params = params.set('bacType', bacTypeRequired);
    }

    return this.http.get<FieldOfStudyDto[]>(`${this.baseUrl}/filter/bac-type`, { params });
  }
}
