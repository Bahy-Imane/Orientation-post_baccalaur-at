import { Component, OnInit } from '@angular/core';
import { FieldOfStudyDto } from "../../../../core/Dto/field-of-study-dto";
import { FieldOfStudyService } from "../../../../core/services/field-of-study-service";
import { FormsModule } from "@angular/forms";
import { FieldOfStudyFormComponent } from "../field-of-study-form/field-of-study-form.component";
import { MatPaginator } from "@angular/material/paginator";
import { NgForOf, NgIf } from "@angular/common";
import { MatButton } from "@angular/material/button";
import { MatSnackBar } from "@angular/material/snack-bar";
import { BacType } from "../../../../core/enums/bac-type";

@Component({
  selector: 'app-field-of-study-list',
  templateUrl: './field-of-study-list.component.html',
  standalone: true,
  imports: [
    FormsModule,
    FieldOfStudyFormComponent,
    MatPaginator,
    NgForOf,
    NgIf,
    MatButton
  ],
  styleUrls: ['./field-of-study-list.component.css']
})
export class FieldOfStudyListComponent implements OnInit {
  fieldsOfStudy: FieldOfStudyDto[] = [];
  searchText = '';
  bacTypeRequired: BacType | null = null; // Nouvelle propriété pour le type de Bac
  minimumBacNote: number | null = null; // Nouvelle propriété pour la note minimale de Bac
  showModal = false;
  selectedFieldOfStudyId: number | null = null;
  selectedFieldOfStudy: FieldOfStudyDto | null = null;
  modalTitle: string = '';

  // Pagination
  totalFields = 0;
  pageSize = 5;
  pageIndex = 0;

  // Options for Bac types (assuming you have these values defined in your BacType enum)
  bacTypeOptions = Object.values(BacType);

  constructor(private fieldOfStudyService: FieldOfStudyService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadFieldsOfStudy();
  }

  // Fetch all fields of study
  loadFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe((data) => {
      this.fieldsOfStudy = data;
      this.totalFields = data.length;
    });
  }

  // Filter fields of study based on search input and filtering criteria
  filteredFieldsOfStudy() {
    return this.fieldsOfStudy.filter(field =>
      field.name.toLowerCase().includes(this.searchText.toLowerCase()) &&
      (this.bacTypeRequired ? field.bacTypeRequired === this.bacTypeRequired : true) &&
      (this.minimumBacNote !== null ? field.minimumBacNote >= this.minimumBacNote : true)
    );
  }

  // Open modal for adding a new field of study
  openModal(fosId?: number): void {
    this.selectedFieldOfStudyId = fosId || null;
    this.showModal = true;
    this.modalTitle = fosId ? 'Edit Field of Study' : 'Add Field of Study';
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedFieldOfStudyId = null;
  }

  // Handle page change event for pagination
  onPageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadFieldsOfStudy();
  }

  editField(id: number): void {
    this.openModal(id);
  }

  deleteField(id: number): void {
    if (confirm('Are you sure you want to delete this Field of study?')) {
      this.fieldOfStudyService.deleteFieldOfStudy(id).subscribe(
        () => {
          this.snackBar.open('Institution deleted successfully', 'Close', { duration: 3000 });
          this.loadFieldsOfStudy();
        },
        (error) => {
          console.error('Error deleting institution:', error);
        }
      );
    }
  }
}
