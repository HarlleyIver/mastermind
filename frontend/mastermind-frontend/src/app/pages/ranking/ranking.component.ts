import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent {

  ranking: { username: string, score: number, attemptsUsed: number }[] = [];

  constructor(private api: ApiService) {
    this.loadRanking();
  }

  loadRanking() {
    this.api.getRanking().subscribe((res: any[]) => {
      this.ranking = res.map(r => ({
        username: r.username,
        score: r.score ?? 0,
        attemptsUsed: r.attemptsUsed
      }));
    });
  }

  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }
}