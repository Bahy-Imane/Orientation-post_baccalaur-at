import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Institution} from "../model/institution";
import {InstitutionDto} from "../Dto/institution-dto";


@Injectable({
  providedIn: 'root',
})
export class InstitutionService {
  private baseUrl = 'http://localhost:8089/api/institutions';

  constructor(private http: HttpClient) {}

  getAllInstitutions(): Observable<Institution[]> {
    return this.http.get<Institution[]>(`${this.baseUrl}/all`);
  }

  getInstitutionById(id: number): Observable<InstitutionDto> {
    return this.http.get<InstitutionDto>(`${this.baseUrl}/${id}`);
  }

  createInstitution(institution: InstitutionDto): Observable<InstitutionDto> {
    return this.http.post<InstitutionDto>(`${this.baseUrl}/add-institution`, institution);
  }

  updateInstitution(id: number, institution: InstitutionDto): Observable<InstitutionDto> {
    return this.http.put<InstitutionDto>(`${this.baseUrl}/${id}`, institution);
  }


  deleteInstitution(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
