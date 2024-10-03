import { Routes } from '@angular/router';
import {LoginComponent} from "./components/Authentication/login/login.component";
import {HomeComponent} from "./components/body/home/home.component";
import {AboutUsComponent} from "./components/body/about-us/about-us.component";
import {ContactUsComponent} from "./components/body/contact-us/contact-us.component";
import {SignUpComponent} from "./components/Authentication/sign-up/sign-up.component";
import {StudentProfileComponent} from "./components/student/student-profile/student-profile.component";
import {HeaderComponent} from "./components/body/header/header.component";
import {SideBarComponent} from "./components/body/side-bar/side-bar.component";
import {AuthGuard} from "./core/services/auth-guard";
import {RoleGuard} from "./core/services/role-guard";
import {Role} from "./core/enums/role";
import {AdminDashboardComponent} from "./components/admin/admin-dashboard/admin-dashboard.component";

export const routes: Routes = [
  {path : '' ,component : LoginComponent},
  {path : 'login' ,component : LoginComponent},
  { path: 'signUp', component: SignUpComponent },
  { path: 'home', component: HomeComponent },

  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard,RoleGuard],
    data: {expectedRole: Role.ADMIN}
  },

  {
    path: 'student-profile',
    component: StudentProfileComponent,
    canActivate: [AuthGuard,RoleGuard],
    data: {expectedRole: Role.STUDENT}
  },




  {path : 'student-profile' , component : StudentProfileComponent},
  {path : 'side-bar' , component : SideBarComponent},

];
