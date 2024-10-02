import { Component, inject } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { BacType } from "../../core/enums/bac-type";
import { AuthService } from "../../core/services/auth.service";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {MatFormField} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-signup',
  standalone: true,
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
  imports: [
    ReactiveFormsModule,
    NgClass,
    NgIf,
    MatFormField,
    MatSelect,
    MatOption,
    NgForOf,
    MatProgressSpinner,
    RouterLink,
    MatInput,
    MatButton
  ]
})
export class SignUpComponent {
  signUpForm!: FormGroup;
  bacTypes = Object.values(BacType);
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

  get userName() {
    return this.signUpForm.get('userName');
  }

  get email() {
    return this.signUpForm.get('email');
  }

  get password() {
    return this.signUpForm.get('password');
  }

  get dateOfBirth() {
    return this.signUpForm.get('dateOfBirth');
  }

  get bacScore() {
    return this.signUpForm.get('bacScore');
  }

  get location() {
    return this.signUpForm.get('location');
  }

  get bacType() {
    return this.signUpForm.get('bacType');
  }
}
