import { Component, OnInit } from '@angular/core';
import { InstitutionDto } from "../../../core/Dto/institution-dto";
import { InstitutionService } from "../../../core/services/institution-service";
import { NgClass, NgForOf } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { HeaderComponent } from "../header/header.component";
import { BacType } from "../../../core/enums/bac-type";
import { FieldOfStudyService } from "../../../core/services/field-of-study-service";
import { HeaderContainerComponent } from "../header-container/header-container.component";
import { FieldOfStudy } from "../../../core/model/field-of-study";
import {FieldOfStudyHomeComponent} from "../field-of-study-home/field-of-study-home.component";
import {AllReviewsComponent} from "../all-reviews/all-reviews.component";

@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css'],
  imports: [
    NgForOf,
    NgClass,
    FormsModule,
    HeaderComponent,
    HeaderContainerComponent,
    FieldOfStudyHomeComponent,
    AllReviewsComponent
  ],
  standalone: true
})
export class InstitutionDetailsComponent implements OnInit {
  institutionId!: number;
  institution!: InstitutionDto;
  fieldOfStudies!: FieldOfStudy[];

  commentText: string = '';

  constructor(
    private institutionService: InstitutionService,
    private route: ActivatedRoute,
    private fieldOfStudyService: FieldOfStudyService
  ) {}

  bacTypes: BacType[] = Object.values(BacType); // Enum converted to array
  bacTypeRequired?: BacType;
  minimumBacNote?: number;
  searchText: string = '';

  ngOnInit(): void {
    this.institutionId = Number(this.route.snapshot.paramMap.get('institutionId'));

    if (this.institutionId) {
      this.getInstitutionById();
      this.getFieldsOfStudyOfInstitution();
    } else {
      console.error('Institution ID not found in route parameters');
    }
  }

  getInstitutionById(): void {
    if (this.institutionId) {
      // Appel du service pour récupérer les détails de l'institution
      this.institutionService.getInstitutionById(this.institutionId).subscribe({
        next: (data) => {
          if (data) {
            this.institution = data;
            console.log('Institution details loaded:', this.institution);
          } else {
            console.warn('No institution data returned from the server');
          }
        },
        error: (err) => {
          console.error('Erreur lors du chargement de l’institution', err);
        }
      });
    }
  }

  getFieldsOfStudyOfInstitution(): void {
    if (this.institutionId) {
      // Appel du service pour récupérer les filières associées à l'institution
      this.fieldOfStudyService.getFieldOfStudiesByInstitutionId(this.institutionId).subscribe({
        next: (data) => {
          if (data && data.length > 0) {
            this.fieldOfStudies = data;
            console.log('Fields of study loaded:', this.fieldOfStudies);
          } else {
            console.warn('No fields of study found for this institution');
          }
        },
        error: (err) => {
          console.error('Erreur lors du chargement des filières', err);
        }
      });
    }
  }

  getStars(rating: number | undefined): boolean[] {
    const stars = [];
    if (rating) {
      for (let i = 1; i <= 5; i++) {
        stars.push(i <= rating);
      }
    }
    return stars;
  }
}
