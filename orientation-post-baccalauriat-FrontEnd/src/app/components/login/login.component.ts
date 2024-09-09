import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  backgroundImageUrl: string = 'https://cdn.builder.io/api/v1/image/assets/TEMP/60e7de3a4967146734916762d6847f8aa165602757dcbba0578d85cf4be6aa8a?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154';
  foregroundImageUrl: string = 'https://cdn.builder.io/api/v1/image/assets/TEMP/b4580d8523945746a021fd6ff5ed421cf170543543485e8839bd3201789e4839?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154';

}
