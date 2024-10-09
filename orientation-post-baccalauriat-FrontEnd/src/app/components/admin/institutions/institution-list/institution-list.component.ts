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
  selectedInstitution: InstitutionDto | null = null;
  modalTitle: string = '';

  institutionName: string = '';
  address: string = '';
  institutionType: InstitutionType | null = null;
  loading: boolean = false;

  institutionTypes: InstitutionType[] = Object.values(InstitutionType); // Liste des types d'institutions

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

  onFilter(): void {
    this.loading = true;
    this.institutionService.filterAndSearchInstitutions(this.institutionType, this.searchText)
      .subscribe((data: InstitutionDto[]) => {
        this.filteredInstitutions = data; // Mettre à jour les résultats filtrés
        this.totalInstitutions = data.length; // Mettre à jour le nombre total après le filtrage
        this.currentPage = 0; // Réinitialiser à la première page après un filtrage
        this.loading = false;
      }, error => {
        console.error('Erreur lors du filtrage des institutions', error);
        this.loading = false;
      });
  }

  filteredProjects(): InstitutionDto[] {
    // Pagination et recherche globale sur la liste filtrée
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
      case InstitutionType.EXECUTIVE_TRAINING_INSTITUTION:
        return 'Executive Training Institution';
      case InstitutionType.HIGHER_INSTITUTE:
        return 'Higher Institute';
      case InstitutionType.RESEARCH_INSTITUTE:
        return 'Research Institute';
      case InstitutionType.POLYTECHNIC_SCHOOL:
        return 'Polytechnic School';
      case InstitutionType.PRIVATE_UNIVERSITY:
        return 'Private University';
      case InstitutionType.PUBLIC_UNIVERSITY:
        return 'Public University';
      case InstitutionType.ROYAL_ACADEMY:
        return 'Royal Academy';
      case InstitutionType.VOCATIONAL_TRAINING_CENTER:
        return 'Vocational Training Center';
      default:
        return 'Unknown';
    }
  }

  openModal(institutionId?: number): void {
    if (institutionId) {
      // Cas d'édition
      this.modalTitle = 'Edit Institution';
      this.selectedInstitutionId = institutionId;
      this.institutionService.getInstitutionById(institutionId).subscribe((institution: InstitutionDto) => {
        this.selectedInstitutionId = institution.institutionId;
        this.showModal = true;
      });
    } else {
      // Cas d'ajout
      this.modalTitle = 'Add Institution';
      this.selectedInstitutionId = null; // Pas d'ID, c'est un ajout
      this.showModal = true;
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedInstitution = null;
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

  editInstitution(id: number): void {
    this.institutionService.getInstitutionById(id).subscribe(
      (institution: InstitutionDto) => {
        this.selectedInstitution = institution;
        this.selectedInstitutionId = id;
        this.showModal = true;
      },
      (error) => {
        console.error('Error fetching institution:', error);
      }
    );
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
}
