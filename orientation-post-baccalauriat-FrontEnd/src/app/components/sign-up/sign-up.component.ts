import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BacType } from "../../core/enums/bac-type";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: 'app-signup',
  standalone: true,
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
})
export class SignUpComponent {
  signupForm: FormGroup;
  bacTypes = Object.values(BacType);
  loading = false;
  failure = false;
  errorMessage: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
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
    if (this.signupForm.valid) {
      this.loading = true;
      const userDto = this.signupForm.value;

      this.authService.signUp(userDto).subscribe({
        next: (response) => {
          this.loading = false;
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.loading = false;
          this.failure = true;
          this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.'; // Vous pouvez personnaliser ce message
        }
      });
    }
  }

  get userName() {
    return this.signupForm.get('userName');
  }

  get email() {
    return this.signupForm.get('email');
  }

  get password() {
    return this.signupForm.get('password');
  }

  get dateOfBirth() {
    return this.signupForm.get('dateOfBirth');
  }

  get bacScore() {
    return this.signupForm.get('bacScore');
  }

  get location() {
    return this.signupForm.get('location');
  }

  get bacType() {
    return this.signupForm.get('bacType');
  }
}
