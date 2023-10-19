import { Component, OnInit } from '@angular/core';

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
  
}
