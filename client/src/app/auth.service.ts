import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private loggedIn = false;

  login() {
    this.loggedIn = true;
    console.log("Logat");
  }

  logout() {
    console.log("Delogat")
    this.loggedIn = false;
  }

  isUserLoggedIn(): boolean {
    return this.loggedIn;
  }

  getAvatar(){
    return "assets/images/avatar.png"
  }

  getUsername(){
    return "madalinaB"
  }
}
