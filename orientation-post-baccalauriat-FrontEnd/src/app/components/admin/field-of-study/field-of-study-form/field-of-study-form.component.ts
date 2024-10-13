import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FieldOfStudyService } from '../../../../core/services/field-of-study-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgForOf, NgIf } from '@angular/common';
import { Institution } from '../../../../core/model/institution';
import { FieldOfStudyDto } from '../../../../core/Dto/field-of-study-dto';
import { DialogModule } from 'primeng/dialog';
import { BacType } from '../../../../core/enums/bac-type';
import { InstitutionService } from '../../../../core/services/institution-service';
import { InstitutionDto } from '../../../../core/Dto/institution-dto';
import { FieldOfStudyListComponent } from '../field-of-study-list/field-of-study-list.component';

@Component({
  selector: 'app-field-of-study-form',
  templateUrl: './field-of-study-form.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    DialogModule,
    ReactiveFormsModule,
  ],
  styleUrls: ['./field-of-study-form.component.css'],
})
export class FieldOfStudyFormComponent implements OnInit {
  @Input() fosId: number | null = null; // ID du domaine d'étude à modifier
  @Input() showModal = false; // Contrôle de la visibilité du modal
  @Output() modalClose = new EventEmitter<void>(); // Émet un événement lorsque le modal est fermé
  @Output() fieldChanged = new EventEmitter<void>(); // Emit when an institution is added or edited

  fieldOfStudyForm!: FormGroup; // Formulaire de domaine d'étude
  institutions: InstitutionDto[] = []; // Liste des institutions
  bacTypes = Object.values(BacType); // Types de Bac
  successMessage: string | null = null; // Message de succès


  constructor(
    private fb: FormBuilder,
    private fieldOfStudyService: FieldOfStudyService,
    private institutionService: InstitutionService,
    private fieldOfStudyList: FieldOfStudyListComponent,
    private snackBar: MatSnackBar
  ) {
    this.fieldOfStudyForm = this.fb.group({
      name: ['', Validators.required],
      bacTypeRequired: ['', Validators.required],
      minimumBacNote: [0, [Validators.required, Validators.min(0), Validators.max(20)]],
      departmentName: ['', Validators.required],
      institutionId: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadInstitutions();
  }


  loadInstitutions(): void {
    this.institutionService.getAllInstitutions().subscribe(
      (institutions) => {
        this.institutions = institutions; // Affectation des institutions à la liste
      },
      (error) => {
        console.error('Erreur lors du chargement des institutions:', error);
        this.snackBar.open('Erreur lors du chargement des institutions', 'Fermer', { duration: 3000 });
      }
    );
  }

  onSubmit(): void {
    if (this.fieldOfStudyForm.valid) {
      const fieldOfStudyData = {
        ...this.fieldOfStudyForm.value
      };

      if (this.fosId) {
        this.fieldOfStudyService.updateFieldOfStudy(this.fosId, fieldOfStudyData).subscribe(
          () => {
            this.successMessage = 'Institution modifiée avec succès';
            this.snackBar.open(this.successMessage, 'Fermer', { duration: 3000 });
            this.fieldOfStudyList.loadFieldsOfStudy();
            this.closeModal();
            this.fieldChanged.emit();
          },
          error => {
            console.error('Erreur lors de la mise à jour de l’institution', error);
          }
        );
      } else {
        this.fieldOfStudyService.addFieldOfStudy(fieldOfStudyData).subscribe(
          () => {
            this.successMessage = 'Institution ajoutée avec succès';
            this.snackBar.open(this.successMessage, 'Fermer', { duration: 3000 });
            this.fieldOfStudyList.loadFieldsOfStudy();
            this.closeModal();
            this.fieldChanged.emit();
          },
          error => {
            console.error('Erreur lors de l’ajout de l’institution', error);
          }
        );
      }
    }
  }

  // onSubmit(): void {
  //   if (this.fieldOfStudyForm.valid) {
  //     const fieldOfStudyDto: FieldOfStudyDto = this.fieldOfStudyForm.value;
  //
  //     const request$ = this.fosId
  //       ? this.fieldOfStudyService.updateFieldOfStudy(this.fosId, fieldOfStudyDto)
  //       : this.fieldOfStudyService.addFieldOfStudy(fieldOfStudyDto);
  //
  //     request$.subscribe({
  //       next: (response) => {
  //         this.successMessage = this.fosId ? 'Domaine d\'étude mis à jour avec succès' : 'Domaine d\'étude ajouté avec succès';
  //         this.snackBar.open(this.successMessage, 'Fermer', { duration: 3000 });
  //         this.fieldOfStudyList.loadFieldsOfStudy(); // Recharge la liste des domaines d'étude
  //         this.closeModal(); // Ferme le modal
  //       },
  //       error: (error) => {
  //         console.error('Erreur:', error);
  //         this.snackBar.open('Erreur lors du traitement de la demande', 'Fermer', { duration: 3000 });
  //       },
  //     });
  //   }
  // }

  closeModal(): void {
    this.showModal = false; // Masque le modal
    this.fieldOfStudyForm.reset(); // Réinitialise le formulaire
    this.modalClose.emit(); // Émet l'événement pour fermer le modal
  }
}
