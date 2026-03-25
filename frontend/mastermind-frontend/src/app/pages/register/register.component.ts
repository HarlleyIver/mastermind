import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  username = '';
  email = '';
  password = '';

  erroUsername = false;
  erroEmail = false;
  erroSenha = false;

  constructor(private api: ApiService, private router: Router) {}

  register() {

    this.erroUsername = false;
    this.erroEmail = false;
    this.erroSenha = false;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!this.username) {
      this.erroUsername = true;
    }

    if (!this.email || !emailRegex.test(this.email)) {
      this.erroEmail = true;
    }

    if (!this.password) {
      this.erroSenha = true;
    }

    if (this.erroUsername || this.erroEmail || this.erroSenha) return;

    const data = {
      username: this.username,
      email: this.email,
      password: this.password
    };

    this.api.register(data).subscribe({
      next: () => {
        alert('Cadastro realizado!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        if (err.error?.message) {
          alert(err.error.message);
        } else {
          alert('Erro ao cadastrar');
        }
      }
    });
  }
}