import { Component, OnInit } from '@angular/core';
import { InstitutionService } from "../../../core/services/institution-service";
import { FieldOfStudyService } from "../../../core/services/field-of-study-service";
import { InstitutionDto } from "../../../core/Dto/institution-dto";
import { FieldOfStudyDto } from "../../../core/Dto/field-of-study-dto";
import { HeaderContainerComponent } from "../../body/header-container/header-container.component";
import { FooterComponent } from "../../body/footer/footer.component";
import { ReviewService } from "../../../core/services/review-service";
import { ReviewDto } from "../../../core/Dto/review-dto";
import {StudentService} from "../../../core/services/student-service";

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [
    HeaderContainerComponent,
    FooterComponent
  ],
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  totalStudents: number = 0;
  totalInstitutions: number = 0;
  totalFieldsOfStudy: number = 0;
  totalReviews: number = 0;

  constructor(
    private studentService: StudentService,
    private institutionService: InstitutionService,
    private fieldOfStudyService: FieldOfStudyService,
    private reviewService: ReviewService,
  ) {}

  ngOnInit(): void {
    this.fetchDashboardData();
  }

  fetchDashboardData() {

    this.studentService.getAllStudents().subscribe({
      next: (students) => {
        console.log(students);
        this.totalStudents = students.length;
      },
      error: (error) => {
        console.error('Error fetching students:', error);
      }
    });
    this.institutionService.getAllInstitutions().subscribe({
      next: (institutions: InstitutionDto[]) => {
        this.totalInstitutions = institutions.length;
      },
      error: (error) => {
        console.error('Error fetching institutions:', error);
      }
    });

    // Fetch total fields of study
    this.fieldOfStudyService.getAllFieldsOfStudy().subscribe({
      next: (fields: FieldOfStudyDto[]) => {
        this.totalFieldsOfStudy = fields.length;
      },
      error: (error) => {
        console.error('Error fetching fields of study:', error);
      }
    });

    // Fetch total reviews
    this.reviewService.getAllReviews().subscribe({
      next: (reviews: ReviewDto[]) => {
        this.totalReviews = reviews.length;
      },
      error: (error) => {
        console.error('Error fetching reviews:', error);
      }
    });
  }
}
