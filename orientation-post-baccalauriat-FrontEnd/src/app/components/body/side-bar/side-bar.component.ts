import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-side-bar',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {

  constructor(public _authService: AuthService) {}

  activeSection :string ='dashboard';

  // welcomeMessage: string = 'Welcome';
  // profilePicUrl: string = 'assets/default-profile.jpg';


  onSelect(section: string) {
    this.activeSection = section;
    console.log('Active Section:', this.activeSection);
  }

}
