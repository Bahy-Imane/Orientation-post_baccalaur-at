import { Component } from '@angular/core';
import {HeaderContainerComponent} from "../header-container/header-container.component";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [
    HeaderContainerComponent,
    RouterOutlet
  ],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent {

}
