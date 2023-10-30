import { Injectable , Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(@Inject(LOCAL_STORAGE) private storage : StorageService) { }

  login() {
    this.storage.set("loged",true);
    console.log("Logat");
  }

  logout() {
    console.log("Delogat")
    this.storage.set("loged",false);
  }

  isUserLoggedIn(): boolean {
    return this.storage.get("loged");
  }

  getAvatar(){
    return "assets/images/avatar.png"
  }

  getUsername(){
    return "madalinaB"
  }
}
