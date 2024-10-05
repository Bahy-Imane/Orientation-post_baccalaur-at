import { Component, OnInit } from '@angular/core';
import {FieldOfStudyDto} from "../../../../core/Dto/field-of-study-dto";
import {FieldOfStudyService} from "../../../../core/services/field-of-study-service";
import {FormsModule} from "@angular/forms";
import {FieldOfStudyFormComponent} from "../field-of-study-form/field-of-study-form.component";
import {MatPaginator} from "@angular/material/paginator";
import {FieldOfStudy} from "../../../../core/model/field-of-study";
import {NgForOf, NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";

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

  // Pagination
  totalFields = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(private fieldOfStudyService: FieldOfStudyService) {}

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
  openModal() {
    this.selectedFieldOfStudyId = null;
    this.showModal = true;
  }

  // Close the modal
  closeModal() {
    this.showModal = false;
    this.loadFieldsOfStudy(); // Refresh the list after add/edit
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
}
