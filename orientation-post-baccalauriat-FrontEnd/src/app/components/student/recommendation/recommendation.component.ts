// recommendations.component.ts
import { Component, OnInit } from '@angular/core';
import {FieldOfStudyDto} from "../../../core/Dto/field-of-study-dto";
import {FieldOfStudyService} from "../../../core/services/field-of-study-service";
import {NgForOf} from "@angular/common";


@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendation.component.html',
  standalone: true,
  imports: [
    NgForOf
  ],
  styleUrls: ['./recommendation.component.css']
})
export class RecommendationComponent implements OnInit {
  recommendations: FieldOfStudyDto[] = [];

  constructor(private fieldOfStudyService: FieldOfStudyService) {}

  ngOnInit(): void {
    this.loadRecommendations();
  }

  loadRecommendations(): void {
    this.fieldOfStudyService.getRecommendation().subscribe(
      (data) => {
        this.recommendations = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des recommandations', error);
      }
    );
  }
}

