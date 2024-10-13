import { Component, Input, OnInit } from '@angular/core';
import { ReviewDto } from '../../../core/Dto/review-dto';
import { ReviewService } from '../../../core/services/review-service';
import { AuthService } from '../../../core/services/auth.service';
import { NgClass, NgForOf, NgIf } from "@angular/common";
import { ActivatedRoute } from "@angular/router";
import { InstitutionFormComponent } from "../../admin/institutions/institution-form/institution-form.component";
import {ReviewFormComponent} from "../review-form/review-form.component";

@Component({
  selector: 'app-all-reviews',
  templateUrl: './all-reviews.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    NgClass,
    InstitutionFormComponent,
    ReviewFormComponent
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
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadReviews();
  }

  // Load reviews based on the institution ID from the route parameters
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
    console.log('Opening modal');
    this.showModal = true;
  }

  closeModal(): void {
    console.log('Closing modal');
    this.showModal = false;
  }


  getStars(rating: number): boolean[] {
    return Array(5).fill(false).map((_, i) => i < rating);
  }
}
