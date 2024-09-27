import { Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {AboutUsComponent} from "./components/about-us/about-us.component";
import {ContactUsComponent} from "./components/contact-us/contact-us.component";

export const routes: Routes = [
  {path : 'home' , component : HomeComponent},
  {path : 'login' ,component : LoginComponent},
  {path : 'signUp',component : SignUpComponent},
  {path : 'about' , component :AboutUsComponent},
  {path : 'contact' , component :ContactUsComponent},

];
