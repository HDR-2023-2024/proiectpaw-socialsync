import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-horizontally',
  templateUrl: './navbar-horizontally.component.html',
  styleUrls: ['./navbar-horizontally.component.css']
})
export class NavbarHorizontallyComponent {
  itemsToDisplay: any[] = [
    { id: 1, name: 'Element 1' },
    { id: 2, name: 'Element 2' },
    { id: 3, name: 'Element 3' },
    { id: 3, name: 'Element 4' },
    { id: 3, name: 'Element 5' },
    { id: 3, name: 'Element 6' },
    { id: 3, name: 'Element 7' },
    { id: 3, name: 'Element 8' },
  ];

  isVisible = true;

  hideTopics() {
    console.log("hideTopics apelat")
    var elem = document.getElementById("topics-list");
    if (elem != null) {
      if (this.isVisible == true) { 
        elem.style.display = 'none' ;
        this.isVisible = false;
        var title = document.getElementById("topics-arrow");
        if(title != null){
          title.innerHTML = "⮟"
        }
      }
      else {
        elem.style.display = 'block' ;
        this.isVisible = true;
        var title = document.getElementById("topics-arrow");
        if(title != null){
          title.innerHTML = "⮝"
        }
      }
    }
  }
}