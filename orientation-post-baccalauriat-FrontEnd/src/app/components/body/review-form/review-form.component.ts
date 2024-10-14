import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { NgClass, NgForOf, NgIf, NgStyle } from "@angular/common";
import { RatingModule } from 'primeng/rating';
import { AllReviewsComponent } from '../all-reviews/all-reviews.component';
import {AuthService} from "../../../core/services/auth.service";
import {ReviewDto} from "../../../core/Dto/review-dto";
import {ReviewService} from "../../../core/services/review-service";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, NgForOf, NgIf, NgClass, NgStyle, RatingModule],
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {
  @Input() institutionId!: number;
  @Output() modalClose = new EventEmitter<void>();
  reviewForm!: FormGroup;
  userName: string | null = null;  // Stocker le nom d'utilisateur

  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewService,
    private authService: AuthService,
    private allReviewList: AllReviewsComponent
  ) {}

  ngOnInit(): void {
    this.userName = this.authService.getUserName();

    this.reviewForm = this.fb.group({
      userName: [{ value: this.userName, disabled: true }],
      rating: [5, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', Validators.required]
    });
    this.allReviewList.loadReviews();
  }

  onSubmit(): void {
    if (this.reviewForm.valid) {
      const reviewData: ReviewDto = {
        institutionId: this.institutionId,
        ...this.reviewForm.getRawValue()  // Récupérer même les champs désactivés
      };

      this.reviewService.createReview(reviewData).subscribe({
        next: (response) => {
          console.log('Review successfully submitted:', response);
          this.modalClose.emit();
          this.reviewForm.reset();
          this.allReviewList.loadReviews();
        },
        error: (error) => {
          console.error('Error submitting review:', error);
        }
      });
    } else {
      console.log('Review form is invalid');
    }
  }

  closeModal(): void {
    this.reviewForm.reset();
    this.modalClose.emit();
  }
}
