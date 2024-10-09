import {Component, OnInit} from '@angular/core';
import {InstitutionDto} from "../../../core/Dto/institution-dto";
import {InstitutionService} from "../../../core/services/institution-service";
import {DecimalPipe, NgClass, NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-institutions-home',
  standalone: true,
  imports: [
    DecimalPipe,
    RouterLink,
    NgForOf,
    NgClass
  ],
  templateUrl: './institutions-home.component.html',
  styleUrl: './institutions-home.component.css'
})
export class InstitutionsHomeComponent  implements OnInit{
  institutions: InstitutionDto[] = [];

  constructor(private institutionService: InstitutionService) {}

  ngOnInit(): void {
    this.institutionService.getAllInstitutions().subscribe(
      (data) => {
        this.institutions = data;
      },
      (error) => {
        console.error('Error fetching institutions', error);
      }
    );
  }

  getStars(rating: number): boolean[] {
    return new Array(5).fill(false).map((_, index) => index < rating);
  }
}
