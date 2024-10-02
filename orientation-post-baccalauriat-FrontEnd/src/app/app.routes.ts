import { Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {AboutUsComponent} from "./components/about-us/about-us.component";
import {ContactUsComponent} from "./components/contact-us/contact-us.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";

export const routes: Routes = [
  {path : '' ,component : LoginComponent},
  {path : 'login' ,component : LoginComponent},
  {path : 'home' , component : HomeComponent},
  { path: 'signUp', component: SignUpComponent },
  {path : 'about' , component :AboutUsComponent},
  {path : 'contact' , component :ContactUsComponent},

];
