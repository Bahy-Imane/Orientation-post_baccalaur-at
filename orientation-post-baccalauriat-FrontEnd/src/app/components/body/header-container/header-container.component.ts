import { Component } from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {NgIf} from "@angular/common";
import {StudentHeaderComponent} from "../student-header/student-header.component";
import {HeaderComponent} from "../header/header.component";

@Component({
  selector: 'app-header-container',
  standalone: true,
  imports: [
    NgIf,
    StudentHeaderComponent,
    HeaderComponent
  ],
  templateUrl: './header-container.component.html',
  styleUrl: './header-container.component.css'
})
export class HeaderContainerComponent {
  constructor(public _authService: AuthService) {}

}
