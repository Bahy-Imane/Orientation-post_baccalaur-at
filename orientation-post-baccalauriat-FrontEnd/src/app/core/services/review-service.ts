import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ReviewDto} from "../Dto/review-dto";

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private baseUrl = 'http://localhost:8089/api/reviews';

  constructor(private http: HttpClient) {}

  getReviewsByInstitution(institutionId: number): Observable<ReviewDto[]> {
    return this.http.get<ReviewDto[]>(`${this.baseUrl}/institution/${institutionId}`);
  }

  addReview(reviewDto: ReviewDto): Observable<ReviewDto> {
    return this.http.post<ReviewDto>(`${this.baseUrl}`, reviewDto);
  }
}
