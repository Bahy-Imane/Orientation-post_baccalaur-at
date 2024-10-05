import { Component, inject } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { LoginDto } from "../../../core/Dto/login-dto";
import { NgClass, NgIf } from "@angular/common";
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    NgIf,
    RouterLink
  ],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  success = false;
  failure = false;
  emailErrorMessage: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      userNameOrEmail: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      rememberMe: [false]
    });
  }

  onSubmit() {
    this.success = false;
    this.failure = false;
    this.emailErrorMessage = null;

    if (this.loginForm.valid) {
      const loginUserDto: LoginDto = {
        userNameOrEmail: this.loginForm.value.userNameOrEmail,
        password: this.loginForm.value.password,
      };

      this.authService.login(loginUserDto).subscribe({
        next: (response) => {
          if (response.accessToken) {
            this.success = true;
            console.log('Token:', response.accessToken);

            localStorage.setItem('accessToken', response.accessToken);
            localStorage.setItem('role', response.role);

            const userRole = response.role;
            console.log('User Role:', userRole);

            if (userRole === "ADMIN") {
              this.router.navigate(['/admin-dashboard']);
            } else if (userRole === "STUDENT") {
              this.router.navigate(['/student-profile']);
            }

          } else {
            this.failure = true;
            this.emailErrorMessage = 'Login failed. Invalid response from server.';
          }
        },
        error: (error) => {
          console.error('Login error:', error);
          this.failure = true;
          this.emailErrorMessage = 'Login failed. Please check your credentials and try again.';
        }
      });
    } else {
      console.log('Formulaire invalide');
    }
  }

  get userNameOrEmail() {
    return this.loginForm.get('userNameOrEmail');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
