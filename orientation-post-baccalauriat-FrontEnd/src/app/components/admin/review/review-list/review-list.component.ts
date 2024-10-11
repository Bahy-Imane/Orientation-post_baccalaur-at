import { Component, OnInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { InstitutionDto } from '../../../../core/Dto/institution-dto';
import { NgForOf, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { InstitutionType } from '../../../../core/enums/institution-type';
import { InstitutionService } from '../../../../core/services/institution-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import {ReviewDto} from "../../../../core/Dto/review-dto";
import {ReviewService} from "../../../../core/services/review-service";
import {TableModule} from "primeng/table";
import {RatingModule} from "primeng/rating";

@Component({
  selector: 'app-institution-list',
  standalone: true,
  imports: [
    MatPaginator,
    NgForOf,
    FormsModule,
    MatButton,
    NgIf,
    TableModule,
    RatingModule
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
  filteredReviews: InstitutionDto[] = [];

  showModal: boolean = false;
  selectedReviewId: number | null = null;
  selectedReview: InstitutionDto | null = null;
  modalTitle: string = '';

  institutionName: string = '';
  address: string = '';
  institutionType: InstitutionType | null = null;
  loading: boolean = false;

  constructor(private reviewService: ReviewService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {
    this.loading = true;
    this.reviewService.getAllReviews().subscribe((data: ReviewDto[]) => {
      this.reviews = data;
      this.totalReviews = data.length;
      this.loading = false;
    });
  }
  //
  // onFilter(): void {
  //   this.loading = true;
  //   this.reviews.filterAndSearchInstitutions(this.institutionType, this.searchText)
  //     .subscribe((data: InstitutionDto[]) => {
  //       this.filteredInstitutions = data; // Mettre à jour les résultats filtrés
  //       this.totalInstitutions = data.length; // Mettre à jour le nombre total après le filtrage
  //       this.currentPage = 0; // Réinitialiser à la première page après un filtrage
  //       this.loading = false;
  //     }, error => {
  //       console.error('Erreur lors du filtrage des institutions', error);
  //       this.loading = false;
  //     });
  // }

  // filteredReview(): InstitutionDto[] {
  //   // Pagination et recherche globale sur la liste filtrée
  //   return this.filteredReviews
  //     .filter(rev => this.searchText === '' || rev.institutionName.toLowerCase().includes(this.searchText.toLowerCase()))
  //     .slice(this.currentPage * this.pageSize, (this.currentPage + 1) * this.pageSize);
  // }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
  }



  // openModal(institutionId?: number): void {
  //   if (institutionId) {
  //     // Cas d'édition
  //     this.modalTitle = 'Edit Institution';
  //     this.selectedReviewId = institutionId;
  //     this.reviewService.getReviewsByInstitutionId(institutionId).subscribe((institution: InstitutionDto) => {
  //       this.selectedInstitutionId = institution.institutionId;
  //       this.showModal = true;
  //     });
  //   } else {
  //     // Cas d'ajout
  //     this.modalTitle = 'Add Institution';
  //     this.selectedInstitutionId = null; // Pas d'ID, c'est un ajout
  //     this.showModal = true;
  //   }
  // }

  // closeModal(): void {
  //   this.showModal = false;
  //   this.selectedInstitution = null;
  // }
  //
  // sort(field: keyof InstitutionDto) {
  //   this.filteredInstitutions.sort((a, b) => {
  //     const aValue = a[field];
  //     const bValue = b[field];
  //     if (aValue < bValue) return -1;
  //     if (aValue > bValue) return 1;
  //     return 0;
  //   });
  // }
  //

  deleteReview(id: number): void {
    if (confirm('Are you sure you want to delete this Review?')) {
      this.reviewService.deleteReview(id).subscribe(
        () => {
          this.snackBar.open('Institution deleted successfully', 'Close', { duration: 3000 });
          this.loadReviews();
        },
        (error) => {
          console.error('Error deleting institution:', error);
        }
      );
    }
  }
}
