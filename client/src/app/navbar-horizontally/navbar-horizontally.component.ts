import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-horizontally',
  templateUrl: './navbar-horizontally.component.html',
  styleUrls: ['./navbar-horizontally.component.css']
})
export class NavbarHorizontallyComponent {
  itemsToDisplay: any[] = [
    { id: 1, name: 'Meniul', subgroup: ["Băuturi", "Mâncare", "Deserturi"] },
    { id: 2, name: 'Băuturi', subgroup: ["Apa", "Sucuri", "Cafea"] },
    { id: 3, name: 'Mâncare', subgroup: ["Pizza", "Paste", "Burgeri"] },
    { id: 4, name: 'Deserturi', subgroup: ["Tort", "Înghețată", "Prăjituri"] },
    { id: 5, name: 'Noutăți', subgroup: ["Evenimente", "Oferte", "Promoții"] },
    { id: 6, name: 'Istorie', subgroup: ["Perioade istorice", "Personalități istorice", "Evenimente istorice"] },
    { id: 7, name: 'Geografie', subgroup: ["Țări", "Capitale", "Munți"] },
    { id: 8, name: 'Sport', subgroup: ["Fotbal", "Tenis", "Baschet"] },
    { id: 9, name: 'Știință', subgroup: ["Astronomie", "Biologie", "Chimie"] },
    { id: 10, name: 'Tehnologie', subgroup: ["Gadget-uri", "Software", "Inovații"] },
  ];

  isVisible = true;
  isVisibleFull = false;
  
  selectViewFull(){
    this.isVisibleFull = !this.isVisibleFull;
  }

  hideTopics() {
    console.log("hideTopics apelat")
    var elem = document.getElementById("topics-list");
    if (elem != null) {
      if (this.isVisible == true) {
        elem.style.display = 'none';
        this.isVisible = false;
        var title = document.getElementById("topics-arrow");
        if (title != null) {
          title.innerHTML = "⮟"
        }
      }
      else {
        elem.style.display = 'block';
        this.isVisible = true;
        var title = document.getElementById("topics-arrow");
        if (title != null) {
          title.innerHTML = "⮝"
        }
      }
    }
  }
}