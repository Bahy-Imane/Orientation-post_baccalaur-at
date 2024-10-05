import {Component, EventEmitter, Output} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-side-bar-admin',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    RouterLinkActive,
    RouterOutlet
  ],
  templateUrl: './side-bar-admin.component.html',
  styleUrl: './side-bar-admin.component.css'
})
export class SideBarAdminComponent {
  activeSection: string = 'profile-admin';

  constructor(public _authService: AuthService) {}

  onSelect(section: string) {
    this.activeSection = section; // Set the active section
  }
}
