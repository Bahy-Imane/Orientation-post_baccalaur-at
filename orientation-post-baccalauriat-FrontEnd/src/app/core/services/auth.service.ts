import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {LoginDto} from "../Dto/login-dto";
import {JwtAuthResponse} from "../Dto/jwt-auth-response";
import {SignUpDto} from "../Dto/sign-up-dto";
import {Role} from "../enums/role";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8089/api/auth';

  constructor(private http: HttpClient , private router : Router) { }

  login(loginDto: LoginDto): Observable<JwtAuthResponse> {
    return this.http.post<JwtAuthResponse>(`${this.baseUrl}/login`, loginDto);
  }

  signUp(signUpDto: SignUpDto): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/signup`, signUpDto);
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('accessToken');
  }

  getUserRole(): Role | null {
    const token = localStorage.getItem('accessToken');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
      return payload.personRole as Role;
    }
    return null;
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('tokenType');
    localStorage.removeItem('userName');
    localStorage.removeItem('role');
    localStorage.removeItem('personId');
    this.router.navigate(['/login'])
  }

}
