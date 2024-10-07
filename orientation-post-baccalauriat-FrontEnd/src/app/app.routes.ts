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
import {AdvicesComponent} from "./components/body/advices/advices.component";
import {InstitutionListComponent} from "./components/admin/institutions/institution-list/institution-list.component";
import {FieldOfStudyService} from "./core/services/field-of-study-service";
import {FieldOfStudyListComponent} from "./components/admin/field-of-study/field-of-study-list/field-of-study-list.component";
import {InstitutionFormComponent} from "./components/admin/institutions/institution-form/institution-form.component";
import {
  FieldOfStudyFormComponent
} from "./components/admin/field-of-study/field-of-study-form/field-of-study-form.component";
import {InstitutionsHomeComponent} from "./components/body/institutions-home/institutions-home.component";


export const routes: Routes = [
  {path : '' ,component : HomeComponent},
  {path : 'login' ,component : LoginComponent},
  { path: 'signUp', component: SignUpComponent },
  { path: 'home' , component : HomeComponent},
  { path: 'about-us', component : AboutUsComponent },
  { path: 'contact-us' , component : ContactUsComponent},
  { path : 'advice' , component : AdvicesComponent},
  { path : 'institutions' , component : InstitutionsHomeComponent},

  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: {expectedRole: Role.ADMIN},
    children:[
      { path : 'institution',
        component : InstitutionListComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: {expectedRole: Role.ADMIN}
      },
      { path : 'field-of-study',
        component : FieldOfStudyListComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: {expectedRole: Role.ADMIN}
      },
      {
        path: 'institution/add',
        component: InstitutionFormComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: Role.ADMIN }
      },
      {
        path: 'institution/edit/:id',
        component: InstitutionFormComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: Role.ADMIN }
      },
      {
        path: 'field-of-study/add/:id',
        component: FieldOfStudyFormComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: Role.ADMIN }
      },
      {
        path: 'field-of-study/edit/:id',
        component: FieldOfStudyFormComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: Role.ADMIN }
      },

    ]
  },

      {path : 'field-of-study' ,
        component : FieldOfStudyListComponent,
        canActivate: [AuthGuard, RoleGuard],
        data: {expectedRole: Role.ADMIN}
      },

  {path : 'student-profile' , component : StudentProfileComponent},
  {path : 'side-bar' , component : SideBarComponent},

];
