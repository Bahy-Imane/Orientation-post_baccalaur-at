import { Component, OnInit } from '@angular/core';
import { InstitutionDto } from "../../../core/Dto/institution-dto";
import { InstitutionService } from "../../../core/services/institution-service";
import { Router } from "@angular/router";
import { InstitutionType } from "../../../core/enums/institution-type";
import {NgClass, NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FooterComponent} from "../footer/footer.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: 'app-institutions-home',
  standalone: true,
  templateUrl: './institutions-home.component.html',
  imports: [
    NgForOf,
    FormsModule,
    NgClass,
    FooterComponent,
    HeaderContainerComponent
  ],
  styleUrls: ['./institutions-home.component.css']
})
export class InstitutionsHomeComponent implements OnInit {
  institutions: InstitutionDto[] = [];
  searchText: string = '';
  selectedType: InstitutionType | null = null;
  selectedRating: number | null = null;
  institutionTypes = Object.values(InstitutionType);

  constructor(private institutionService: InstitutionService, private router: Router) {}

  ngOnInit(): void {
    this.loadAllInstitutions();
  }

  loadAllInstitutions(): void {
    this.institutionService.getAllInstitutions().subscribe(
      (data) => {
        this.institutions = data;
        console.log(data);
      },
      (error) => {
        console.error('Error fetching institutions', error);
      }
    );
  }

  searchByKeywords(): void {
    this.institutionService.searchInstitutions(this.searchText).subscribe(
      (data) => {
        this.institutions = data;
      },
      (error) => {
        console.error('Error fetching institutions by keywords', error);
      }
    );
  }

  filterByType(): void {
    if (this.selectedType) {
      this.institutionService.filterInstitutionsByType(this.selectedType).subscribe(
        (data) => {
          this.institutions = data;
        },
        (error) => {
          console.error('Error filtering institutions by type', error);
        }
      );
    } else {
      this.loadAllInstitutions();
    }
  }

  filterByRating(): void {
    if (this.selectedRating) {
      this.institutionService.filterInstitutionsByRating(this.selectedRating).subscribe(
        (data) => {
          this.institutions = data;
        },
        (error) => {
          console.error('Error filtering institutions by rating', error);
        }
      );
    } else {
      this.loadAllInstitutions();
    }
  }

  resetFilters(): void {
    this.searchText = '';
    this.selectedType = null;
    this.selectedRating = null;
    this.loadAllInstitutions(); // Reload all institutions
  }

  showDetails(institutionId: number): void {
    this.router.navigate(['institution-details', institutionId]);
  }

  getStars(rating: number): boolean[] {
    return new Array(5).fill(false).map((_, index) => index < rating);
  }
}
