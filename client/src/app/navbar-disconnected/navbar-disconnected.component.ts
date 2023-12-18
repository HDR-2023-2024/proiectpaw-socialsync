import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

interface InputData {
  stringName: string;
}

@Component({
  selector: 'app-navbar-disconnected',
  templateUrl: './navbar-disconnected.component.html',
  styleUrls: ['./navbar-disconnected.component.css']
})


export class NavbarDisconnectedComponent implements OnInit {
  @Input() inputData: InputData | null = null; // in ce caut

  ngOnInit(): void {
    const searchInput = document.getElementById("search-input") as HTMLInputElement;

    if (searchInput) {
      searchInput.addEventListener("keyup", function (event) {
        if (event.key === "Enter") {
          const inputValue = searchInput.value;
          console.log(inputValue);
        }
      });
    }
  }
  isHomeLink: boolean = false;
  isShowUserMenu = false;

  constructor(private router: Router, public authService: AuthService) { }


  navigateMobileMenu() {
    this.isHomeLink = !this.isHomeLink;

    if (this.isHomeLink) {
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/home-menu']);
    }
  }

  onLoginClick() {
    this.router.navigate(['/login']);
  }

  showUserMenu() {
    this.isShowUserMenu = !this.isShowUserMenu;
  }

  onLogOutClick() {
    this.authService.logout();
    this.isShowUserMenu = false;
  }

  onSignUpClick(): void {
    this.router.navigate(['/create-account']);
  }

  onEnterKeyPressed() {
    const searchInput = document.getElementById("search-input") as HTMLInputElement;
    const inputValue = searchInput.value;
    if (inputValue.length == 1) {
      alert("Nu se poate căuta după o singură literă.");
      return;
    }
    
    if (this.inputData != null && this.inputData.stringName.includes("com")) {
      console.log("Cautare in topicuri!");
      this.router.navigate(['/view-topics', { query: inputValue }]);
    } else if (this.inputData != null && this.inputData.stringName.includes("uti")) {
      console.log("Cautare in postari!")
      this.router.navigate(['/home', { query: inputValue }]);
    }else{
      console.log("Cautare in postari!")
      this.router.navigate(['/home', { query: inputValue }]);
    }
  }
}
