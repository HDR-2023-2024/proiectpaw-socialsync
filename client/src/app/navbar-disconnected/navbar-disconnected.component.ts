import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-disconnected',
  templateUrl: './navbar-disconnected.component.html',
  styleUrls: ['./navbar-disconnected.component.css']
})
export class NavbarDisconnectedComponent implements OnInit {
  ngOnInit(): void {
    const searchInput = document.getElementById("search-input") as HTMLInputElement;

    if (searchInput) {
      searchInput.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
          const inputValue = searchInput.value;
          console.log( inputValue);
        }
      });
    }
  }
  isHomeLink: boolean = false;

  constructor(private router: Router) {}


  navigateMobileMenu() {
    this.isHomeLink = !this.isHomeLink; 

    if (this.isHomeLink) {
      this.router.navigate(['/home']); 
    } else {
      this.router.navigate(['/home-menu']); 
    }
  }
}
