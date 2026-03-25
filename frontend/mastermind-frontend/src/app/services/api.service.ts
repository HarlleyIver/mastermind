import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private url = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(this.url + '/user/login', data);
  }

  register(data: any) {
    return this.http.post(this.url + '/user/register', data);
  }

  startGame(userId: number) {
    return this.http.post(this.url + '/game/start?userId=' + userId, {});
  }

  attempt(data: any) {
    return this.http.post(this.url + '/game/attempt', data);
  }

  getRanking() {
    return this.http.get<any[]>(this.url + '/game/ranking');
  }
}