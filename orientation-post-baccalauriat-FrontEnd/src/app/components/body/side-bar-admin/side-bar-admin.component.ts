import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-side-bar-admin',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './side-bar-admin.component.html',
  styleUrl: './side-bar-admin.component.css'
})
export class SideBarAdminComponent {

  constructor(public _authService: AuthService) {}

  activeSection :string ='dashboard';

  // welcomeMessage: string = 'Welcome';
  // profilePicUrl: string = 'assets/default-profile.jpg';


  onSelect(section: string) {
    this.activeSection = section;
    console.log('Active Section:', this.activeSection);
  }
}
