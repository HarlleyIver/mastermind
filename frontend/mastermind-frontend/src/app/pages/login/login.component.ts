import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  email = '';
  senha = '';

  erroEmail = false;
  erroSenha = false;

  constructor(private api: ApiService, private router: Router) {}

  login() {

    this.erroEmail = false;
    this.erroSenha = false;

    if (!this.email) {
      this.erroEmail = true;
    }

    if (!this.senha) {
      this.erroSenha = true;
    }

    if (this.erroEmail || this.erroSenha) return;

    this.api.login({
      login: this.email,
      password: this.senha
    }).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('user', JSON.stringify(res.user));
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        alert('Login inválido');
      }
    });
  }
}