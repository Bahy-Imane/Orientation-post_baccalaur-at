import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ReviewDto} from "../Dto/review-dto";

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private baseUrl = 'http://localhost:8081/api/reviews'; // Your API base URL

  constructor(private http: HttpClient) {}

  // Fetch reviews by institution ID
  getReviewsByInstitutionId(institutionId: number): Observable<ReviewDto[]> {
    return this.http.get<ReviewDto[]>(`${this.baseUrl}/institution/${institutionId}`);
  }

  // Create a new review
  createReview(reviewDto: ReviewDto): Observable<ReviewDto> {
    return this.http.post<ReviewDto>(this.baseUrl, reviewDto);
  }

  // Update a review
  updateReview(reviewId: number, reviewDto: ReviewDto): Observable<ReviewDto> {
    return this.http.put<ReviewDto>(`${this.baseUrl}/edit/${reviewId}`, reviewDto);
  }

  // Delete a review
  deleteReview(reviewId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${reviewId}`);
  }
}
