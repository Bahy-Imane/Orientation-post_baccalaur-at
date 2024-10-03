import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  constructor(public _authService: AuthService) {}

  activeSection :string ='dashboard';

  onSelect(section: string) {
    this.activeSection = section;
    console.log('Active Section:', this.activeSection);
  }

}
