import { Component } from '@angular/core';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
import {NgForOf} from "@angular/common";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: 'app-advices',
  standalone: true,
    imports: [
        HeaderComponent,
        FooterComponent,
        NgForOf,
        HeaderContainerComponent
    ],
  templateUrl: './advices.component.html',
  styleUrl: './advices.component.css'
})
export class AdvicesComponent {

  collaborations: number = 250;
  partners: string[] = ["Partner1", "Partner2", "Partner3", "Partner4"];
}
