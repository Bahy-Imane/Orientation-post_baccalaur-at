import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InstitutionDto} from "../Dto/institution-dto";
import {InstitutionType} from "../enums/institution-type";


@Injectable({
  providedIn: 'root',
})
export class InstitutionService {
  private apiUrl = 'http://localhost:8082/api/institutions';

  constructor(private http: HttpClient) {}

  // Fetch all institutions
  getAllInstitutions(): Observable<InstitutionDto[]> {
    return this.http.get<InstitutionDto[]>(`${this.apiUrl}/all-institutions`);
  }

  // Fetch an institution by ID
  getInstitutionById(institutionId: number): Observable<InstitutionDto> {
    return this.http.get<InstitutionDto>(`${this.apiUrl}/${institutionId}`);
  }

  // Create a new institution
  createInstitution(institutionDto: InstitutionDto): Observable<InstitutionDto> {
    return this.http.post<InstitutionDto>(`${this.apiUrl}/add-institution`, institutionDto);
  }

  // Update an institution
  updateInstitution(id: number, institutionDto: InstitutionDto): Observable<InstitutionDto> {
    return this.http.put<InstitutionDto>(`${this.apiUrl}/edit/${id}`, institutionDto);
  }

  // Delete an institution
  deleteInstitution(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  // Filter and sort institutions
  filterAndSortInstitutions(
    institutionType?: InstitutionType,
    institutionName?: string,
    address?: string
  ): Observable<InstitutionDto[]> {
    let params = new HttpParams();
    if (institutionType) {
      params = params.append('institutionType', institutionType);
    }
    if (institutionName) {
      params = params.append('institutionName', institutionName);
    }
    if (address) {
      params = params.append('address', address);
    }
    return this.http.get<InstitutionDto[]>(`${this.apiUrl}/filter`, { params });
  }
}
