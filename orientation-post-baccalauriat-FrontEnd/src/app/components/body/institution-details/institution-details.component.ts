import { Component, Input, OnInit } from '@angular/core';
import {InstitutionDto} from "../../../core/Dto/institution-dto";
import {InstitutionService} from "../../../core/services/institution-service";
import {NgClass, NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {HeaderComponent} from "../header/header.component";
import {BacType} from "../../../core/enums/bac-type";
import {HttpParams} from "@angular/common/http";
import {FieldOfStudyService} from "../../../core/services/field-of-study-service";
import {HeaderContainerComponent} from "../header-container/header-container.component";


@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css'],
  imports: [
    NgForOf,
    NgClass,
    FormsModule,
    HeaderComponent,
    HeaderContainerComponent
  ],
  standalone: true
})
export class InstitutionDetailsComponent implements OnInit {
  institutionId!: number;
  institution!: InstitutionDto;
  commentText: string = '';
  constructor(private institutionService: InstitutionService,private route : ActivatedRoute,private fieldOfStudyService: FieldOfStudyService) {}

  bacTypes: BacType[] = Object.values(BacType); // Enum converted to array
  bacTypeRequired?: BacType;
  minimumBacNote?: number;
  searchText: string = '';


  ngOnInit(): void {
    this.getInstitutionById()
    console.log(this.institutionId)
  }


  getInstitutionById(): void {
    const institutionId = Number(this.route.snapshot.paramMap.get('institutionId'));
    if (institutionId) {
      this.institutionService.getInstitutionById(institutionId).subscribe({
        next: (data) => {
          this.institution = data;
        },
        error: (err) => {
          console.error('Erreur lors du chargement de l’institution', err);
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

  // submitComment(): void {
  //   if (this.commentText.trim() !== '') {
  //     console.log('Commentaire envoyé:', this.commentText);
  //     this.commentText = '';
  //   } else {
  //     console.warn('Le champ de commentaire est vide');
  //   }
  // }
  //
  // onSearch(): void {
  //   this.fieldOfStudyService.filterAndSearchFields(this.bacTypeRequired, this.minimumBacNote, this.searchText).subscribe((response) => {
  //     console.log(response);
  //   });
  // }


}
