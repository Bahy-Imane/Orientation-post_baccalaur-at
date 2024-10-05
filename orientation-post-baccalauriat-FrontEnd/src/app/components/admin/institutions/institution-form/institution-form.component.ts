import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { InstitutionDto } from "../../../../core/Dto/institution-dto";
import { InstitutionService } from "../../../../core/services/institution-service";
import { NgForOf, NgIf } from "@angular/common";
import { InstitutionType } from "../../../../core/enums/institution-type";
import { Router } from "@angular/router";
import { Subject } from 'rxjs';
import {InstitutionListComponent} from "../institution-list/institution-list.component";

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
  institutionForm!: FormGroup;
  institutionTypes = Object.values(InstitutionType);
  successMessage: string = '';
  modalSubject: Subject<boolean> = new Subject<boolean>();

  constructor(private fb: FormBuilder, private institutionService: InstitutionService, private router: Router,private institutionList:InstitutionListComponent) {
    this.institutionForm = this.fb.group({
      institutionName: ['', [Validators.required]],
      description: ['', [Validators.required]],
      address: ['', [Validators.required]],
      website: ['', [Validators.required, Validators.pattern('https?://.+')]],
      institutionType: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    this.openModal();
  }

  openModal(): void {
    if (!this.institutionId) {
      this.institutionForm.reset();
      this.successMessage = '';
    } else {
      this.institutionService.getInstitutionById(this.institutionId).subscribe((institution) => {
        this.institutionForm.patchValue({
          institutionName: institution.institutionName,
          description: institution.description,
          address: institution.address,
          website: institution.website,
          institutionType: institution.institutionType,
        });
      });
    }
    this.modalSubject.next(true);
  }

  onSubmit(): void {
    if (this.institutionForm.valid) {
      const institution: InstitutionDto = this.institutionForm.value;
      if (this.institutionId) {
        this.institutionService.updateInstitution(this.institutionId, institution).subscribe(() => {
          this.successMessage = 'Institution mise à jour avec succès!';
          this.closeModal();
        });
      } else {
        this.institutionService.createInstitution(institution).subscribe(() => {
          this.successMessage = 'Institution ajoutée avec succès!';
          this.closeModal();
          this.institutionList.loadInstitutions();
        });
      }
    }
  }



  closeModal(): void {
    this.modalClose.emit();
    this.router.navigate(['/admin-dashboard/institution']);
    this.modalSubject.next(false); // Ferme la modal
  }
}
