import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { BacType } from '../../core/enums/bac-type';
import { Interest } from '../../core/enums/interest';
import { SignUpDto } from '../../core/Dto/sign-up-dto';
import {MatButton, MatButtonModule} from '@angular/material/button';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatInput, MatInputModule} from '@angular/material/input';
import {MatOption, MatSelect, MatSelectModule} from '@angular/material/select';
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-signup',
  templateUrl: './sign-up.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  signupForm: FormGroup;
  bacTypes = Object.values(BacType);
  interests = Object.values(Interest);
  success = false;
  failure = false;
  errorMessage: string | null = null;
  showPassword = false;

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  private authService = inject(AuthService);
  private router = inject(Router);

  constructor(private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      bacScore: ['', [Validators.required, Validators.min(0), Validators.max(20)]],
      location: ['', Validators.required],
      bacType: ['', Validators.required],
      interest: ['', Validators.required]
    });
  }

  get f() {
    return this.signupForm.controls;
  }

  onSignup(): void {
    if (this.signupForm.valid) {
      const userDto: SignUpDto = this.signupForm.value;

      this.authService.signUp(userDto).subscribe({
        next: () => {
          this.success = true;
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.failure = true;
          this.errorMessage = 'Signup failed. Please try again.';
          console.error(error);
        }
      });
    }
  }

  // Access form control properties using bracket notation
  getUserNameControl() {
    return this.signupForm.controls['userName'];
  }

  getEmailControl() {
    return this.signupForm.controls['email'];
  }

  getPasswordControl() {
    return this.signupForm.controls['password'];
  }

  getBacScoreControl() {
    return this.signupForm.controls['bacScore'];
  }

  getLocationControl() {
    return this.signupForm.controls['location'];
  }

  getBacTypeControl() {
    return this.signupForm.controls['bacType'];
  }

  getInterestControl() {
    return this.signupForm.controls['interest'];
  }
}
