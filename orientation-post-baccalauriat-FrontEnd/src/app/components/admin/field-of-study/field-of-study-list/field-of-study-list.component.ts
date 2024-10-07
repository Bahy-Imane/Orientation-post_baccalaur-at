import { Component, OnInit } from '@angular/core';
import {FieldOfStudyDto} from "../../../../core/Dto/field-of-study-dto";
import {FieldOfStudyService} from "../../../../core/services/field-of-study-service";
import {FormsModule} from "@angular/forms";
import {FieldOfStudyFormComponent} from "../field-of-study-form/field-of-study-form.component";
import {MatPaginator} from "@angular/material/paginator";
import {FieldOfStudy} from "../../../../core/model/field-of-study";
import {NgForOf, NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatSnackBar} from "@angular/material/snack-bar";
import {InstitutionDto} from "../../../../core/Dto/institution-dto";
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
  fieldsOfStudy: FieldOfStudy[] = [];
  searchText = '';
  showModal = false;
  selectedFieldOfStudyId: number | null = null;
  selectedFieldOfStudy: FieldOfStudyDto | null = null;
  modalTitle: string = '';
  // Pagination
  totalFields = 0;
  pageSize = 5;
  pageIndex = 0;


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
  // Filter fields of study based on search input
  filteredFieldsOfStudy() {
    return this.fieldsOfStudy.filter(field =>
      field.name.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }
  // Open modal for adding a new field of study
  openModal(fosId?: number): void {
    if (fosId) {
      this.modalTitle = 'Edit Institution';
      this.selectedFieldOfStudyId = fosId;
      this.fieldOfStudyService.getFieldOfStudyById(fosId).subscribe((fieldOfStudy: FieldOfStudyDto) => {
        this.selectedFieldOfStudyId = fieldOfStudy.fosId;
        this.showModal = true;
      });
    } else {
      // Cas d'ajout
      this.modalTitle = 'Add Institution';
      this.selectedFieldOfStudyId = null; // Pas d'ID, c'est un ajout
      this.showModal = true;
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedFieldOfStudyId = null;
  }
  // // Edit an existing field of study
  // editFieldOfStudy(field: FieldOfStudyDto) {
  //   this.selectedFieldOfStudyId = field.id; // Assuming `id` exists
  //   this.showModal = true;
  // }
  //
  // // Delete a field of study
  // deleteFieldOfStudy(fieldId: number) {
  //   this.fieldOfStudyService.deleteFieldOfStudy(fieldId).subscribe(() => {
  //     this.fetchFieldsOfStudy(); // Refresh the list after deletion
  //   });
  // }
  // Handle page change event for pagination
  onPageChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadFieldsOfStudy();
  }

  editField(id: number): void {
    this.fieldOfStudyService.getFieldOfStudyById(id).subscribe(
      (fieldOfStudy: FieldOfStudyDto) => {
        this.selectedFieldOfStudy = fieldOfStudy;
        this.selectedFieldOfStudyId = id;
        this.showModal = true;
      },
      (error) => {
        console.error('Error fetching institution:', error);
      }
    );
  }

  deleteField(id: number): void {
    if (confirm('Are you sure you want to delete this Field of study?')) {
      this.fieldOfStudyService.deleteFieldOfStudy(id).subscribe(
        () => {
          this.snackBar.open('Field of study deleted successfully', 'Close', { duration: 3000 });
          this.loadFieldsOfStudy();
        },
        (error) => {
          console.error('Error deleting institution:', error);
        }
      );
    }
  }
}
