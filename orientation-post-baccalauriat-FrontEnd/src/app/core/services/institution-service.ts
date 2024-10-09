import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InstitutionDto} from "../Dto/institution-dto";
import {InstitutionType} from "../enums/institution-type";

@Injectable({
  providedIn: 'root',
})
export class InstitutionService {
  private baseUrl = 'http://localhost:8081/api/institutions';

  constructor(private http: HttpClient) {}

  getAllInstitutions(): Observable<InstitutionDto[]> {
    return this.http.get<InstitutionDto[]>(`${this.baseUrl}/all-institutions`);
  }

  getInstitutionById(institutionId: number): Observable<InstitutionDto> {
    return this.http.get<InstitutionDto>(`${this.baseUrl}/${institutionId}`);
  }

  createInstitution(institutionDto: InstitutionDto): Observable<InstitutionDto> {
    return this.http.post<InstitutionDto>(`${this.baseUrl}/add-institution`, institutionDto);
  }

  updateInstitution(id: number, institutionDto: InstitutionDto): Observable<InstitutionDto> {
    return this.http.put<InstitutionDto>(`${this.baseUrl}/edit/${id}`, institutionDto);
  }

  deleteInstitution(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  filterAndSearchInstitutions(
    institutionType?: InstitutionType | null,
    searchText?: string
  ): Observable<InstitutionDto[]> {
    let params = new HttpParams();

    if (institutionType) {
      params = params.append('institutionType', institutionType);
    }
    if (searchText) {
      params = params.append('searchText', searchText);
    }

    return this.http.get<InstitutionDto[]>(`${this.baseUrl}/filter-institutions`, { params });
  }
}
