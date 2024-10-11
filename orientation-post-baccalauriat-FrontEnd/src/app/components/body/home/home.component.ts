import {Component, ElementRef, ViewChild} from '@angular/core';
import { NgForOf } from "@angular/common";
import {LoginComponent} from "../../Authentication/login/login.component";
import {AdminDashboardComponent} from "../../admin/admin-dashboard/admin-dashboard.component";
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";
import {HeaderContainerComponent} from "../header-container/header-container.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgForOf,
    AdminDashboardComponent,
    HeaderComponent,
    FooterComponent,
    HeaderContainerComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  institutions = [
    { name: 'Université Mohammed V - Rabat', image: 'assets/pictures/Université-Mohammed-V-Rabat.jpg', location: 'Rabat, Maroc' },
    { name: 'Université Cadi Ayyad', image: 'assets/pictures/2280836982723262542.jpg', location: 'Marrakech, Maroc' },
    { name: 'Université Hassan II', image: 'assets/pictures/universitas-hasan-ii-casablanca.jpg', location: 'Casablanca, Maroc' },

    { name: 'École Mohammadia d\'Ingénieurs (EMI)', image: 'assets/pictures/ecole mohamadia.jpg', location: 'Rabat, Maroc' },
    { name: 'École Nationale Supérieure des Mines de Rabat (ENSMR)', image: 'assets/pictures/ENIM.png', location: 'Rabat, Maroc' },
    { name: 'École Nationale des Sciences Appliquées (ENSA)', image: 'assets/pictures/Ensa_fes.jpg', location: 'Tanger, Maroc' }
  ];

  startIndex = 0;
  endIndex = 3;
  visibleInstitutions = this.institutions.slice(this.startIndex, this.endIndex);

  // Affiche les établissements précédents
  previousCards(): void {
    if (this.startIndex > 0) {
      this.startIndex -= 3;
      this.endIndex -= 3;
      this.updateVisibleInstitutions();
    }
  }

  // Affiche les établissements suivants
  nextCards(): void {
    if (this.endIndex < this.institutions.length) {
      this.startIndex += 3;
      this.endIndex += 3;
      this.updateVisibleInstitutions();
    }
  }

  // Met à jour les établissements visibles
  updateVisibleInstitutions(): void {
    this.visibleInstitutions = this.institutions.slice(this.startIndex, this.endIndex);
  }
}
