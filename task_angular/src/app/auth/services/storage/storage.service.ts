import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() {}

  static saveToken(token: string): void {
    window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: any): void {
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(): string | null {
    return localStorage.getItem(TOKEN);
  }

  static getUser(): any {
    const userString = localStorage.getItem(USER);
    return userString ? JSON.parse(userString) : null;
  }

  static getUserRole(): string {
    const user = this.getUser();
    return user?.role ?? '';
  }

  static isAdminLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;

    const role = this.getUserRole();
    return role === 'ADMIN';
  }

  static isEmployeeLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;

    const role = this.getUserRole();
    return role === 'EMPLOYEE';
  }

  static getUserId(): string {
    const user = this.getUser();
    return user?.id ?? '';
  }

  static logout(): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
