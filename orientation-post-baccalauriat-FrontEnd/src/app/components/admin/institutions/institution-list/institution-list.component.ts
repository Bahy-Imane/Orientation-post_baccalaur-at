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

  constructor(private institutionService: InstitutionService,private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadInstitutions();
  }

  loadInstitutions(): void {
    this.institutionService.getAllInstitutions().subscribe((data: InstitutionDto[]) => {
      this.institutions = data;
      this.filteredInstitutions = data;
      this.totalInstitutions = data.length;
    });
  }

  editInstitution(id: number): void {
    this.selectedInstitutionId = id;
    this.showModal = true;
  }

  deleteInstitution(id: number): void {
    if (confirm('Are you sure you want to delete this institution?')) {
      this.institutionService.deleteInstitution(id).subscribe(
        () => {
          this.snackBar.open('Institution deleted successfully', 'Close', { duration: 3000 });
          this.loadInstitutions();
        },
        (error) => {
          console.error('Error deleting institution:', error);
        }
      );
    }
  }



  filteredProjects(): InstitutionDto[] {
    return this.filteredInstitutions
      .filter(inst => this.searchText === '' || inst.institutionName.toLowerCase().includes(this.searchText.toLowerCase()))
      .slice(this.currentPage * this.pageSize, (this.currentPage + 1) * this.pageSize);
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

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
  }

  getInstitutionTypeLabel(type: InstitutionType): string {
    switch (type) {
      case InstitutionType.EXECUTIVE_TRAINING_INSTITUTION:
        return 'EXECUTIVE_TRAINING_INSTITUTION';
      case InstitutionType.HIGHER_INSTITUTE:
        return 'HIGHER_INSTITUTE';
      case InstitutionType.RESEARCH_INSTITUTE:
        return 'RESEARCH_INSTITUTE';
      case InstitutionType.POLYTECHNIC_SCHOOL:
        return 'POLYTECHNIC_SCHOOL';
      case InstitutionType.PRIVATE_UNIVERSITY:
        return 'PRIVATE_UNIVERSITY';
      case InstitutionType.PUBLIC_UNIVERSITY:
        return 'PUBLIC_UNIVERSITY';
      case InstitutionType.ROYAL_ACADEMY:
        return 'ROYAL_ACADEMY';
      case InstitutionType.VOCATIONAL_TRAINING_CENTER:
        return 'VOCATIONAL_TRAINING_CENTER';
      default:
        return 'Unknown';
    }
  }

  openModal(institutionId?: number): void {
    this.selectedInstitutionId = institutionId || null;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }
}
