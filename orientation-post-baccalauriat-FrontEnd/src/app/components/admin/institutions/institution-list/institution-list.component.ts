import { Component, OnInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { InstitutionDto } from '../../../../core/Dto/institution-dto';
import { NgForOf, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { InstitutionType } from '../../../../core/enums/institution-type';
import { InstitutionService } from '../../../../core/services/institution-service';
import { InstitutionFormComponent } from '../institution-form/institution-form.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-institution-list',
  standalone: true,
  imports: [
    MatPaginator,
    NgForOf,
    FormsModule,
    MatButton,
    InstitutionFormComponent,
    NgIf
  ],
  templateUrl: './institution-list.component.html',
  styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {
  searchText: string = '';
  currentPage: number = 0;
  pageSize: number = 5;
  totalInstitutions: number = 0;

  institutions: InstitutionDto[] = [];
  filteredInstitutions: InstitutionDto[] = [];

  showModal: boolean = false;
  selectedInstitutionId: number | null = null;
  modalTitle: string = '';

  institutionName: string = '';
  address: string = '';
  institutionType: InstitutionType | null = null;
  loading: boolean = false;

  institutionTypes: InstitutionType[] = Object.values(InstitutionType); // List of institution types

  constructor(private institutionService: InstitutionService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadInstitutions();
  }

  loadInstitutions(): void {
    this.loading = true;
    this.institutionService.getAllInstitutions().subscribe((data: InstitutionDto[]) => {
      this.institutions = data;
      this.filteredInstitutions = data;
      this.totalInstitutions = data.length;
      this.loading = false;
    });
  }

  filteredProjects(): InstitutionDto[] {
    return this.filteredInstitutions
      .filter(inst => this.searchText === '' || inst.institutionName.toLowerCase().includes(this.searchText.toLowerCase()))
      .slice(this.currentPage * this.pageSize, (this.currentPage + 1) * this.pageSize);
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
  }

  getInstitutionTypeLabel(type: InstitutionType): string {
    switch (type) {
      case InstitutionType.EXECUTIVE_TRAINING_INSTITUTION: return 'Executive Training Institution';
      case InstitutionType.HIGHER_INSTITUTE: return 'Higher Institute';
      case InstitutionType.RESEARCH_INSTITUTE: return 'Research Institute';
      case InstitutionType.POLYTECHNIC_SCHOOL: return 'Polytechnic School';
      case InstitutionType.PRIVATE_UNIVERSITY: return 'Private University';
      case InstitutionType.PUBLIC_UNIVERSITY: return 'Public University';
      case InstitutionType.ROYAL_ACADEMY: return 'Royal Academy';
      case InstitutionType.VOCATIONAL_TRAINING_CENTER: return 'Vocational Training Center';
      default: return 'Unknown';
    }
  }

  openModal(institutionId?: number): void {
    if (institutionId) {
      this.modalTitle = 'Edit Institution';
      this.selectedInstitutionId = institutionId;
      this.institutionService.getInstitutionById(institutionId).subscribe((institution: InstitutionDto) => {
        this.institutionName = institution.institutionName;
        this.address = institution.address;
        this.institutionType = institution.institutionType;
        this.showModal = true;
      });
    } else {
      this.modalTitle = 'Add Institution';
      this.selectedInstitutionId = null;
      this.institutionName = '';
      this.address = '';
      this.institutionType = null;
      this.showModal = true;
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedInstitutionId = null; // Clear the selected institution
    this.loadInstitutions(); // Reload the institutions to reflect changes
  }

  editInstitution(institutionId: number): void {
    this.openModal(institutionId);
  }

  deleteInstitution(institutionId: number): void {
    if (confirm('Are you sure you want to delete this institution?')) {
      this.institutionService.deleteInstitution(institutionId).subscribe(
        () => {
          this.snackBar.open('Institution deleted successfully', 'Close', { duration: 3000 });
          this.loadInstitutions(); // Reload the institutions after deletion
        },
        (error) => {
          console.error('Error deleting institution:', error);
        }
      );
    }
  }

  sort(field: keyof InstitutionDto) {
    this.filteredInstitutions.sort((a, b) => {
      const aValue = a[field];
      const bValue = b[field];
      if (aValue < bValue) return -1;
      if (aValue > bValue) return 1;
      return 0;
    });
  }
}
