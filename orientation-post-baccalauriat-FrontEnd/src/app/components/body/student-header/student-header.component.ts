import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-student-header',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './student-header.component.html',
  styleUrl: './student-header.component.css'
})
export class StudentHeaderComponent {

  constructor(public _authService: AuthService) {}
}
