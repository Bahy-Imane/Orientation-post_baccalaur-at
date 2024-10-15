import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {Sidebar, SidebarModule} from 'primeng/sidebar';import {NgIf} from "@angular/common";
import {Button} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {StyleClassModule} from "primeng/styleclass";
import {AvatarModule} from "primeng/avatar";
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-side-bar-admin',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    RouterLinkActive,
    RouterOutlet,
    SidebarModule,
    Button,
    Ripple,
    StyleClassModule,
    AvatarModule
  ],
  templateUrl: './side-bar-admin.component.html',
  styleUrl: './side-bar-admin.component.css'
})
export class SideBarAdminComponent {

  activeSection: string = 'admin-dashboard';

 constructor(public _authService: AuthService) {}

   onSelect(section: string) {
     this.activeSection = section; // Set the active section
   }
}
