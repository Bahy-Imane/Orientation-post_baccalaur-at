import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FieldOfStudyDto} from "../../../../core/Dto/field-of-study-dto";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {FieldOfStudyService} from "../../../../core/services/field-of-study-service";
import {InstitutionService} from "../../../../core/services/institution-service";
import {Institution} from "../../../../core/model/institution";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-field-of-study-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './field-of-study-form.component.html',
  styleUrl: './field-of-study-form.component.css'
})
export class FieldOfStudyFormComponent {
  fieldOfStudyForm: FormGroup;
  institutions: Institution[] = [];

  constructor(
    private fb: FormBuilder,
    private institutionService: InstitutionService,
    private fieldOfStudyService: FieldOfStudyService
  ) {
    this.fieldOfStudyForm = this.fb.group({
      name: ['', [Validators.required]],
      bacTypeRequired: ['', [Validators.required]],
      minimumBacNote: ['', [Validators.required, Validators.min(0)]],
      departmentName: ['', [Validators.required]],
      institution: [null, [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.loadInstitutions();
  }

  loadInstitutions(): void {
    this.institutionService.getAllInstitutions().subscribe((data) => {
      this.institutions = data;
    });
  }

  onSubmit(): void {
    const fieldOfStudy: FieldOfStudyDto = this.fieldOfStudyForm.value;
    this.fieldOfStudyService.addFieldOfStudy(fieldOfStudy).subscribe();
  }

  closeModal(): void {
  }
}
