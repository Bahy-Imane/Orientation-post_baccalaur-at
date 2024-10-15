import { Component, OnInit } from '@angular/core';
import { InstitutionDto } from "../../../core/Dto/institution-dto";
import { InstitutionService } from "../../../core/services/institution-service";
import { ActivatedRoute } from "@angular/router";
import { BacType } from "../../../core/enums/bac-type";
import { FieldOfStudyService } from "../../../core/services/field-of-study-service";
import { FieldOfStudy } from "../../../core/model/field-of-study";
import {FieldOfStudyHomeComponent} from "../field-of-study-home/field-of-study-home.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";
import {NgClass, NgForOf} from "@angular/common";
import {AllReviewsComponent} from "../all-reviews/all-reviews.component";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css'],
  imports: [
    FieldOfStudyHomeComponent,
    HeaderContainerComponent,
    NgForOf,
    NgClass,
    AllReviewsComponent,
    FormsModule
  ],
  standalone: true
})
export class InstitutionDetailsComponent implements OnInit {
  institutionId!: number;
  institution!: InstitutionDto;
  fieldOfStudies: FieldOfStudy[] = [];
  bacTypes = Object.values(BacType);
  searchText: string = '';
  selectedBacType: BacType | null = null;

  constructor(
    private institutionService: InstitutionService,
    private route: ActivatedRoute,
    private fieldOfStudyService: FieldOfStudyService
  ) {}

  ngOnInit(): void {
    this.institutionId = Number(this.route.snapshot.paramMap.get('institutionId'));
    if (this.institutionId) {
      this.loadInstitutionDetails();
      this.loadFieldsOfStudy();
    } else {
      console.error('Institution ID not found in route parameters');
    }
  }

  loadInstitutionDetails(): void {
    this.institutionService.getInstitutionById(this.institutionId).subscribe({
      next: data => this.institution = data || {},
      error: err => console.error('Error loading institution', err)
    });
  }

  loadFieldsOfStudy(): void {
    this.fieldOfStudyService.getFieldOfStudiesByInstitutionId(this.institutionId).subscribe({
      next: data => this.fieldOfStudies = data || [],
      error: err => console.error('Error loading fields of study', err)
    });
  }

  getStars(rating: number | undefined): boolean[] {
    return Array.from({ length: 5 }, (_, i) => i < (rating || 0));
  }

  searchFields(): void {
    this.fieldOfStudyService.searchFieldsOfStudy(this.searchText || '').subscribe(
      data => this.fieldOfStudies = data,
      error => console.error('Error fetching fields of study by keywords', error)
    );
  }

  filterByBacType(): void {
    if (this.selectedBacType) {
      this.fieldOfStudyService.filterFieldsOfStudyByBacType(this.selectedBacType).subscribe(
        data => this.fieldOfStudies = data,
        error => console.error('Error filtering fields of study by Bac type', error)
      );
    } else {
      this.loadFieldsOfStudy();
    }
  }

  resetFilters(): void {
    this.searchText = '';
    this.selectedBacType = null;
    this.loadFieldsOfStudy();
  }
}
