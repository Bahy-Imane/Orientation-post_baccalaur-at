import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../../core/services/auth.service";
import {SideBarComponent} from "../../body/side-bar/side-bar.component";
import {SideBarAdminComponent} from "../../body/side-bar-admin/side-bar-admin.component";
import {ProfileComponent} from "../profile/profile.component";
import {InstitutionListComponent} from "../institutions/institution-list/institution-list.component";
import {InstitutionFormComponent} from "../institutions/institution-form/institution-form.component";
import {FieldOfStudyListComponent} from "../field-of-study/field-of-study-list/field-of-study-list.component";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    SideBarComponent,
    SideBarAdminComponent,
    ProfileComponent,
    InstitutionListComponent,
    InstitutionFormComponent,
    FieldOfStudyListComponent,
    RouterOutlet,
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {


}
