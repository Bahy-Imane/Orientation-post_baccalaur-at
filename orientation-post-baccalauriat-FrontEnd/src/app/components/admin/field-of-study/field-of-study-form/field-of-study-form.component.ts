import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FieldOfStudyService } from '../../../../core/services/field-of-study-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgForOf, NgIf } from '@angular/common';
import { Institution } from '../../../../core/model/institution';
import { FieldOfStudyDto } from '../../../../core/Dto/field-of-study-dto';
import {DialogModule} from "primeng/dialog";

@Component({
  selector: 'app-field-of-study-form',
  templateUrl: './field-of-study-form.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    DialogModule,
  ],
  styleUrls: ['./field-of-study-form.component.css'],
})
export class FieldOfStudyFormComponent implements OnInit {
  @Input() showModal = false;
  @Input() institutions: Institution[] = [];
  fieldOfStudyForm!: FormGroup;
  @Input() fieldOfStudyId: number | null = null;
  @Output() modalClose = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private fieldOfStudyService: FieldOfStudyService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.fieldOfStudyForm = this.fb.group({
      name: ['', Validators.required],
      bacTypeRequired: ['', Validators.required],
      minimumBacNote: [0, [Validators.required, Validators.min(0), Validators.max(20)]],
      departmentName: ['', Validators.required],
      institutionId: [null, Validators.required],
    });
  }

  onSubmit(): void {
    if (this.fieldOfStudyForm.valid) {
      const fieldOfStudyDto: FieldOfStudyDto = this.fieldOfStudyForm.value;
      const institutionId = fieldOfStudyDto.institutionId; // Récupérer l'ID de l'institution depuis le formulaire

      this.fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto).subscribe({
        next: (response) => {
          this.snackBar.open('Field of Study added successfully', 'Close', { duration: 3000 });
          this.closeModal();
        },
        error: (error) => {
          console.error('Error adding field of study:', error);
          this.snackBar.open('Error adding field of study', 'Close', { duration: 3000 });
        },
      });
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.fieldOfStudyForm.reset();
    this.modalClose.emit(); // Émettre l'événement pour fermer le modal
  }
}
