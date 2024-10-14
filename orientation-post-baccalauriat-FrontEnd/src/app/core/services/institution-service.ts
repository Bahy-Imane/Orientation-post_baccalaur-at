import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InstitutionDto} from "../Dto/institution-dto";
import {InstitutionType} from "../enums/institution-type";


@Injectable({
  providedIn: 'root'
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

  createInstitution(institutionDto: InstitutionDto): Observable<{ msg: string }> {
    return this.http.post<{ msg: string }>(`${this.baseUrl}/add-institution`, institutionDto);
  }

  updateInstitution(id: number, institutionDto: InstitutionDto): Observable<{ msg: string }> {
    return this.http.put<{ msg: string }>(`${this.baseUrl}/edit/${id}`, institutionDto);
  }

  deleteInstitution(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }


  searchInstitutions(searchText?: string): Observable<InstitutionDto[]> {
    let params = new HttpParams();
    if (searchText) {
      params = params.set('searchText', searchText);
    }

    return this.http.get<InstitutionDto[]>(`${this.baseUrl}/search`, { params });
  }

  filterInstitutionsByType(type?: InstitutionType | null): Observable<InstitutionDto[]> {
    let params = new HttpParams();
    if (type) {
      params = params.set('type', type);
    }

    return this.http.get<InstitutionDto[]>(`${this.baseUrl}/filter/type`, { params });
  }

  filterInstitutionsByRating(minRating?: number | null): Observable<InstitutionDto[]> {
    let params = new HttpParams();
    if (minRating) {
      params = params.set('minRating', minRating.toString());
    }

    return this.http.get<InstitutionDto[]>(`${this.baseUrl}/filter/rating`, { params });
  }
}
