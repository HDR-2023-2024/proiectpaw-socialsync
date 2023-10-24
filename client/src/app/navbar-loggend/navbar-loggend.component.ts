import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-navbar-loggend',
  templateUrl: './navbar-loggend.component.html',
  styleUrls: ['./navbar-loggend.component.css']
})
export class NavbarLoggendComponent {
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
