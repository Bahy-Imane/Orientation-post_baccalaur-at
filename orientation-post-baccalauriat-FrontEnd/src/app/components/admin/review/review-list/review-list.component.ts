import { Component, OnInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReviewDto } from "../../../../core/Dto/review-dto";
import { ReviewService } from "../../../../core/services/review-service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [
    MatPaginator,
    NgForOf,

  ],
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.css']
})
export class ReviewListComponent implements OnInit {
  searchText: string = '';
  currentPage: number = 0;
  pageSize: number = 5;
  totalReviews: number = 0;

  reviews: ReviewDto[] = [];
  filteredReviews: ReviewDto[] = [];

  showModal: boolean = false;
  selectedReviewId: number | null = null;
  modalTitle: string = '';

  constructor(private reviewService: ReviewService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {
    this.reviewService.getAllReviews().subscribe((data: ReviewDto[]) => {
      this.reviews = data;
      this.totalReviews = data.length;
      this.filteredReviews = data; // Keep a reference to the filtered list for pagination
    });
  }

  // Method to paginate the reviews
  paginatedReviews(): ReviewDto[] {
    const startIndex = this.currentPage * this.pageSize;
    return this.filteredReviews.slice(startIndex, startIndex + this.pageSize);
  }

  deleteReview(id: number): void {
    if (confirm('Are you sure you want to delete this Review?')) {
      this.reviewService.deleteReview(id).subscribe(
        () => {
          this.snackBar.open('Review deleted successfully', 'Close', { duration: 3000 });
          this.loadReviews();
        },
        (error) => {
          console.error('Error deleting review:', error);
        }
      );
    }
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
  }
}
