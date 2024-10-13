// src/app/components/field-of-study-home/field-of-study-home.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FieldOfStudyDto } from '../../../core/Dto/field-of-study-dto';
import { InstitutionDto } from '../../../core/Dto/institution-dto';
import { InstitutionService } from '../../../core/services/institution-service';
import { FieldOfStudyService } from '../../../core/services/field-of-study-service';
import {FieldOfStudy} from "../../../core/model/field-of-study";
import {HeaderContainerComponent} from "../header-container/header-container.component";
import {FooterComponent} from "../footer/footer.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-field-of-study-home',
  templateUrl: './field-of-study-home.component.html',
  standalone: true,
  imports: [
    HeaderContainerComponent,
    FooterComponent,
    NgForOf
  ],
  styleUrls: ['./field-of-study-home.component.css']
})
export class FieldOfStudyHomeComponent implements OnInit {
  institution: InstitutionDto | undefined;
  fieldsOfStudy: FieldOfStudy[] = [];

  constructor(
    private route: ActivatedRoute,
    private institutionService: InstitutionService,
    private fieldOfStudyService: FieldOfStudyService
  ) {}

  ngOnInit(): void {
    const institutionId = Number(this.route.snapshot.paramMap.get('institutionId'));

    // Récupérer les détails de l'institution
    this.institutionService.getInstitutionById(institutionId).subscribe((data) => {
      this.institution = data;

      // Récupérer les champs d'études associés à l'institution
      this.fieldOfStudyService.getFieldOfStudiesByInstitutionId(institutionId).subscribe((fields) => {
        this.fieldsOfStudy = fields;
      });
    });
  }
}
