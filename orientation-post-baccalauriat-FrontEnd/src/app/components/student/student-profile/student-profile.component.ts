import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../../core/services/auth.service";
import {SideBarComponent} from "../../body/side-bar/side-bar.component";
import {HomeComponent} from "../../body/home/home.component";

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    SideBarComponent,
    HomeComponent
  ],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent {


}
