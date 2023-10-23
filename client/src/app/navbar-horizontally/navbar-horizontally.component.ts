import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-horizontally',
  templateUrl: './navbar-horizontally.component.html',
  styleUrls: ['./navbar-horizontally.component.css']
})
export class NavbarHorizontallyComponent implements OnInit {

  ngOnInit(): void {
    var myArr: any[] = [
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

    this.itemsToDisplay = myArr;
  }

  itemsToDisplay?: any[]

  isVisible = true;
  isVisibleFull = false;
  isTopicsVisible = true;
  isResourcesVisible = true;

  selectViewFull() {
    this.isVisibleFull = !this.isVisibleFull;
  }

  hideTopics() {
    this.isTopicsVisible = !this.isTopicsVisible;
    console.log(this.isTopicsVisible)
  }

  hideResources() {
    this.isResourcesVisible = !this.isResourcesVisible;
  }
}