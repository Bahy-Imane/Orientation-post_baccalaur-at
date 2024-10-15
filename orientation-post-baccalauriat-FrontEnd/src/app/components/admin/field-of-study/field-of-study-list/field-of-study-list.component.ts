import { Component, OnInit } from '@angular/core';
import { FieldOfStudyDto } from "../../../../core/Dto/field-of-study-dto";
import { FieldOfStudyService } from "../../../../core/services/field-of-study-service";
import { FormsModule } from "@angular/forms";
import { FieldOfStudyFormComponent } from "../field-of-study-form/field-of-study-form.component";
import { MatPaginator, PageEvent } from "@angular/material/paginator";
import { NgForOf, NgIf } from "@angular/common";
import { MatButton } from "@angular/material/button";
import { MatSnackBar } from "@angular/material/snack-bar";
import { BacType } from "../../../../core/enums/bac-type";
import { TableModule } from "primeng/table";

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
    MatButton,
    TableModule
  ],
  styleUrls: ['./field-of-study-list.component.css']
})
export class FieldOfStudyListComponent implements OnInit {
  fieldsOfStudy: FieldOfStudyDto[] = [];
  filteredFieldsOfStudy: FieldOfStudyDto[] = [];
  searchText = '';
  bacTypeRequired: BacType | null = null;
  showModal = false;
  selectedFieldOfStudyId: number | null = null;
  modalTitle: string = '';

  totalFields = 0;
  pageSize = 5; // Default page size
  currentPage = 0; // Default to first page

  bacTypeOptions = Object.values(BacType);

  constructor(private fieldOfStudyService: FieldOfStudyService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadFieldsOfStudy();
  }

  loadFieldsOfStudy() {
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe((data) => {
      this.fieldsOfStudy = data;
      this.filteredFieldsOfStudy = data;
      this.totalFields = data.length;
    });
  }

  onSearch() {
    this.fieldOfStudyService.searchFieldsOfStudy(this.searchText).subscribe(
      (results) => {
        this.filteredFieldsOfStudy = results;
        this.totalFields = results.length;
        this.currentPage = 0; // Reset to the first page when searching
      },
      (error) => {
        console.error('Error searching fields of study:', error);
      }
    );
  }

  onFilter() {
    this.fieldOfStudyService.filterFieldsOfStudyByBacType(this.bacTypeRequired).subscribe(
      (results) => {
        this.filteredFieldsOfStudy = results;
        this.totalFields = results.length;
        this.currentPage = 0; // Reset to the first page when filtering
      },
      (error) => {
        console.error('Error filtering fields of study by Bac Type:', error);
      }
    );
  }

  openModal(fosId?: number): void {
    this.selectedFieldOfStudyId = fosId || null;
    this.showModal = true;
    this.modalTitle = fosId ? 'Edit Field of Study' : 'Add Field of Study';
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedFieldOfStudyId = null;
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
  }

  get paginatedFields(): FieldOfStudyDto[] {
    const startIndex = this.currentPage * this.pageSize;
    return this.filteredFieldsOfStudy.slice(startIndex, startIndex + this.pageSize);
  }

  editField(id: number): void {
    this.openModal(id);
  }

  deleteField(id: number): void {
    if (confirm('Are you sure you want to delete this Field of Study?')) {
      this.fieldOfStudyService.deleteFieldOfStudy(id).subscribe(
        () => {
          this.snackBar.open('Field of Study deleted successfully', 'Close', { duration: 3000 });
          this.loadFieldsOfStudy();
        },
        (error) => {
          console.error('Error deleting Field of Study:', error);
        }
      );
    }
  }

  resetFilters() {
    this.searchText = '';
    this.bacTypeRequired = null;
    this.loadFieldsOfStudy();
  }
}
