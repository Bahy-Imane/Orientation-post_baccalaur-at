import {Component, OnInit} from '@angular/core';
import {InstitutionDto} from "../../../core/Dto/institution-dto";
import {InstitutionService} from "../../../core/services/institution-service";
import {DecimalPipe, NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-institutions-home',
  standalone: true,
  imports: [
    DecimalPipe,
    RouterLink,
    NgForOf
  ],
  templateUrl: './institutions-home.component.html',
  styleUrl: './institutions-home.component.css'
})
export class InstitutionsHomeComponent  implements OnInit{
  institutions: InstitutionDto[] = [];

  constructor(private institutionService: InstitutionService) {}

  ngOnInit(): void {
    this.getAllInstitutions();
  }

  getAllInstitutions(): void {
    this.institutionService.getAllInstitutions().subscribe({
      next: (data) => {
        this.institutions = data;
      },
      error: (err) => {
        console.error('Error fetching institutions', err);
      }
    });
  }
}
