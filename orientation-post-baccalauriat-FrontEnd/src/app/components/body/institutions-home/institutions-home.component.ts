import {Component, OnInit} from '@angular/core';
import {InstitutionDto} from "../../../core/Dto/institution-dto";
import {InstitutionService} from "../../../core/services/institution-service";
import {DecimalPipe, NgClass, NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
import {InstitutionType} from "../../../core/enums/institution-type";
import {FormsModule} from "@angular/forms";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: 'app-institutions-home',
  standalone: true,
  imports: [
    DecimalPipe,
    RouterLink,
    NgForOf,
    NgClass,
    HeaderComponent,
    FooterComponent,
    FormsModule,
    HeaderContainerComponent
  ],
  templateUrl: './institutions-home.component.html',
  styleUrl: './institutions-home.component.css'
})
export class InstitutionsHomeComponent  implements OnInit{
  institutions: InstitutionDto[] = [];
  institutionType: InstitutionType | null = null;
  searchText: string = '';

  // Use the enum to populate the dropdown
  institutionTypes = Object.values(InstitutionType);

  constructor(private institutionService: InstitutionService, private router :Router) {}

  ngOnInit(): void {
    this.institutionService.getAllInstitutions().subscribe(
      (data) => {
        this.institutions = data;
        console.log(data)
      },
      (error) => {
        console.error('Error fetching institutions', error);
      }
    );
  }

  getStars(rating: number): boolean[] {
    return new Array(5).fill(false).map((_, index) => index < rating);
  }

  showDetails(institutionId: number): void {
    this.router.navigate(['institution-details', institutionId]);
  }

  onSearch(): void {
    this.institutionService.filterAndSearchInstitutions(this.institutionType, this.searchText)
      .subscribe((institutions: InstitutionDto[]) => {
        console.log(institutions); // Process and display the results as needed
      });
  }
}
