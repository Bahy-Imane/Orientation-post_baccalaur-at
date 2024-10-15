// all-reviews.component.ts

import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReviewDto } from '../../../core/Dto/review-dto';
import { ReviewService } from '../../../core/services/review-service';
import { AuthService } from '../../../core/services/auth.service';
import { NgClass, NgForOf, NgIf } from "@angular/common";
import { ActivatedRoute } from "@angular/router";
import { InstitutionFormComponent } from "../../admin/institutions/institution-form/institution-form.component";
import { ReviewFormComponent } from "../review-form/review-form.component";
import { ButtonDirective } from "primeng/button";

@Component({
  selector: 'app-all-reviews',
  templateUrl: './all-reviews.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    NgClass,
    InstitutionFormComponent,
    ReviewFormComponent,
    ButtonDirective
  ],
  styleUrls: ['./all-reviews.component.css']
})
export class AllReviewsComponent implements OnInit {
  reviews: ReviewDto[] = [];
  showModal: boolean = false;
  selectedInstitutionId: number | null = null;

  constructor(
    private reviewService: ReviewService,
    public authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {
    const institutionId = this.route.snapshot.paramMap.get('institutionId');
    this.selectedInstitutionId = institutionId ? +institutionId : null;

    if (this.selectedInstitutionId) {
      this.reviewService.getReviewsByInstitutionId(this.selectedInstitutionId).subscribe(
        (reviews: ReviewDto[]) => {
          this.reviews = reviews;
        },
        error => {
          console.error('Error fetching reviews:', error);
        }
      );
    }
  }

  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  getStars(rating: number): boolean[] {
    return Array(5).fill(false).map((_, i) => i < rating);
  }

  editReview(review: ReviewDto): void {
    this.router.navigate(['/edit-review', review.reviewId]);
  }

  deleteReview(reviewId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet avis ?')) {
      this.reviewService.deleteReview(reviewId).subscribe(
        () => {
          this.reviews = this.reviews.filter(review => review.reviewId !== reviewId);
        },
        error => {
          console.error('Error deleting review:', error);
        }
      );
    }
  }
}
