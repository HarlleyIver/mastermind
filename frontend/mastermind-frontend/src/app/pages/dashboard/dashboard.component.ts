import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  constructor(private router: Router) {}

  startGame() {
    this.router.navigate(['/game']);
  }

  goToRanking() {
    this.router.navigate(['/ranking']);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login'], { replaceUrl: true });
  }
}