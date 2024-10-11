import { Component } from '@angular/core';
import {FooterComponent} from "../footer/footer.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";
import {FormsModule} from "@angular/forms";
import {InstitutionDto} from "../../../core/Dto/institution-dto";
import {InstitutionType} from "../../../core/enums/institution-type";
import {InstitutionService} from "../../../core/services/institution-service";
import {Router} from "@angular/router";
import {FieldOfStudyDto} from "../../../core/Dto/field-of-study-dto";
import {BacType} from "../../../core/enums/bac-type";
import {FieldOfStudyService} from "../../../core/services/field-of-study-service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-field-of-study-home',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderContainerComponent,
    FormsModule,
    NgForOf
  ],
  templateUrl: './field-of-study-home.component.html',
  styleUrl: './field-of-study-home.component.css'
})
export class FieldOfStudyHomeComponent {

  fieldsOfStudy: FieldOfStudyDto[] = [];
  bacTypeRequired: BacType | null = null;
  searchText: string = '';

  bacTypesRequired = Object.values(InstitutionType);

  constructor(private fieldOfStudyService: FieldOfStudyService, private router :Router) {}

  ngOnInit(): void {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe(
      (data) => {
        this.fieldsOfStudy = data;
        console.log(data)
      },
      (error) => {
        console.error('Error fetching institutions', error);
      }
    );
  }
}
