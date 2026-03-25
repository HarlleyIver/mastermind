import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent {

  gameId: number | null = null;
  guess: string[] = [];

  colors = ['red', 'blue', 'green', 'yellow', 'purple', 'orange'];

  history: { guess: string[], feedbackArray: string[] }[] = [];

  rows = Array(10).fill(null);

  secretCode: string[] = [];
  isGameOver = false;
  hasGameStarted = false;

  constructor(private api: ApiService) {}

  startGame() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');

    if (!user.id) {
      alert('Usuário não encontrado.');
      return;
    }

    this.api.startGame(user.id).subscribe({
      next: (res: any) => {
        this.gameId = res.id;
        this.guess = [];
        this.history = [];
        this.secretCode = [];
        this.isGameOver = false;
        this.hasGameStarted = true;
      }
    });
  }

  addColor(color: string) {
    if (!this.hasGameStarted) return;
    if (this.isGameOver) return;
    if (this.guess.length >= 4) return;
    if (this.guess.includes(color)) return;

    this.guess.push(color);
  }

  removeLast() {
    if (!this.hasGameStarted) return;
    if (this.isGameOver) return;
    this.guess.pop();
  }

  sendAttempt() {
    if (!this.hasGameStarted) {
      alert('Clique em "Novo Jogo" para começar!');
      return;
    }

    if (this.isGameOver) return;
    if (!this.gameId) return;
    if (this.guess.length !== 4) return;

    const guessString = this.guess.map(c => this.colorToLetter(c)).join('');

    this.api.attempt({
      gameId: this.gameId,
      guess: guessString
    }).subscribe((res: any) => {

      let feedbackArray = [
        ...Array(res.correctPositions).fill('correct'),
        ...Array(res.correctColors).fill('partial')
      ];

      feedbackArray = feedbackArray.sort(() => Math.random() - 0.5);

      while (feedbackArray.length < 4) {
        feedbackArray.push('');
      }

      this.history.push({
        guess: [...this.guess],
        feedbackArray
      });

      this.guess = [];

      if (res.win || res.lose) {

        if (res.secret) {
          this.secretCode = res.secret
            .split('')
            .map((l: string) => this.getColorFromLetter(l));
        }

        this.gameId = null;
        this.isGameOver = true;
        this.hasGameStarted = false;

        if (res.win) {
          alert('🎉 Você ganhou!');
        } else {
          alert('😢 Você perdeu!');
        }
      }
    });
  }

  colorToLetter(color: string): string {
    switch(color) {
      case 'red': return 'A';
      case 'blue': return 'B';
      case 'green': return 'C';
      case 'yellow': return 'D';
      case 'purple': return 'E';
      case 'orange': return 'F';
      default: return 'X';
    }
  }

  getColorFromLetter(letter: string): string {
    switch(letter) {
      case 'A': return 'red';
      case 'B': return 'blue';
      case 'C': return 'green';
      case 'D': return 'yellow';
      case 'E': return 'purple';
      case 'F': return 'orange';
      default: return 'gray';
    }
  }

  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }
}