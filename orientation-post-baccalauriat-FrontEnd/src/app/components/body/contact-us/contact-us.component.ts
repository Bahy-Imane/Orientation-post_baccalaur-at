import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { Component } from "@angular/core";
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: "my-component, MyComponent",
  templateUrl: './contact-us.component.html',
  styleUrl: './contact-us.component.css',
    imports: [
        HeaderComponent,
        FooterComponent,
        HeaderContainerComponent
    ],
  standalone: true
})

export class ContactUsComponent {}
