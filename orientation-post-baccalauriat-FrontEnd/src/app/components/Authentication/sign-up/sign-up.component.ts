import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { BacType } from "../../../core/enums/bac-type";
import { AuthService } from "../../../core/services/auth.service";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-signup',
  standalone: true,
  templateUrl: './sign-up.component.html',
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    RouterLink
  ],
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  signUpForm!: FormGroup;
  bacTypes = Object.values(BacType); // Array contenant les types de bac
  loading = false;
  success = false;
  failure = false;
  errorMessage: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);

  constructor(private fb: FormBuilder) {
    this.signUpForm = this.fb.group({
      userName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: ['', [Validators.required]],
      bacScore: ['', [Validators.required, Validators.min(0), Validators.max(20)]],
      location: ['', [Validators.required]],
      bacType: ['', [Validators.required]],
    });
  }

  onSignup() {
    if (this.signUpForm.valid) {
      this.loading = true;
      const userDto = this.signUpForm.value;

      this.authService.signUp(userDto).subscribe({
        next: (response) => {
          this.loading = false;
          this.success = true;
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.loading = false;
          this.failure = true;
          this.errorMessage = 'Signup failed. Please try again.';
        }
      });
    }
  }
}
