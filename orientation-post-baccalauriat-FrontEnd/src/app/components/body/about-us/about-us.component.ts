import { Component } from '@angular/core';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: 'app-about-us',
  standalone: true,
    imports: [
        HeaderComponent,
        FooterComponent,
        HeaderContainerComponent
    ],
  templateUrl: './about-us.component.html',
  styleUrl: './about-us.component.css'
})
export class AboutUsComponent {

}
