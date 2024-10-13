import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { InstitutionDto } from "../../../../core/Dto/institution-dto";
import { InstitutionService } from "../../../../core/services/institution-service";
import { NgForOf, NgIf } from "@angular/common";
import { InstitutionType } from "../../../../core/enums/institution-type";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-institution-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './institution-form.component.html',
  styleUrls: ['./institution-form.component.css']
})
export class InstitutionFormComponent implements OnInit {
  @Input() institutionId: number | null = null;
  @Output() modalClose = new EventEmitter<void>();
  @Output() institutionChanged = new EventEmitter<void>(); // Emit when an institution is added or edited
  @Input() showModal = false;
  institutionForm!: FormGroup;
  institutionTypes = Object.values(InstitutionType);
  successMessage: string = '';
  logoFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private institutionService: InstitutionService,
    private snackBar: MatSnackBar
  ) {
    this.institutionForm = this.fb.group({
      institutionName: ['', Validators.required],
      description: ['', Validators.required],
      address: ['', Validators.required],
      website: ['', [Validators.required, Validators.pattern('https?://.+')]],
      institutionType: ['', Validators.required],
      logo: [null]
    });
  }

  ngOnInit(): void {
    this.loadInstitution();
  }

  loadInstitution(): void {
    if (this.institutionId) {
      this.institutionService.getInstitutionById(this.institutionId).subscribe((institution: InstitutionDto) => {
        this.institutionForm.patchValue(institution);
      });
    }
  }

  onFileSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.logoFile = file;
    }
  }

  onSubmit(): void {
    if (this.institutionForm.valid) {
      const institutionData = {
        ...this.institutionForm.value,
        logo: this.logoFile
      };

      if (this.institutionId) {
        this.institutionService.updateInstitution(this.institutionId, institutionData).subscribe(
          () => {
            this.successMessage = 'Institution modifiée avec succès';
            this.snackBar.open(this.successMessage, 'Fermer', { duration: 3000 });
            this.loadInstitution();
            this.closeModal();
            this.institutionChanged.emit();
          },
          error => {
            console.error('Erreur lors de la mise à jour de l’institution', error);
          }
        );
      } else {
        this.institutionService.createInstitution(institutionData).subscribe(
          () => {
            this.successMessage = 'Institution ajoutée avec succès';
            this.snackBar.open(this.successMessage, 'Fermer', { duration: 3000 });
            this.loadInstitution();
            this.closeModal();
            this.institutionChanged.emit();
          },
          error => {
            console.error('Erreur lors de l’ajout de l’institution', error);
          }
        );
      }
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.institutionForm.reset();
    this.modalClose.emit();
  }
}
