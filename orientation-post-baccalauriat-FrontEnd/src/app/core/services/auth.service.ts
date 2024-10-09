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
  private baseUrl = 'http://localhost:8081/api/auth';

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

  getUserRole(): Role {
    const role = localStorage.getItem('role');
    return <Role>role;
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
