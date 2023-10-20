import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent {
  //@Input() data: any;
  start = 1;

  myArr: any[] = [
    { id: 1, name: 'Meniul1',author:"author1", img: "assets/images/1.jpg", url: "/home" },
    { id: 2, name: 'Meniul2',author:"author2",  img: "assets/images/2.jpg", url: "/home" },
    { id: 3, name: 'Meniul3',author:"author3",  img: "assets/images/3.jpg", url: "/home" },
    { id: 4, name: 'Meniul4',author:"author4",  img: "assets/images/4.jpg", url: "/home" },
    { id: 5, name: 'Meniul5',author:"author5",  img: "assets/images/5.jpg", url: "/home" },
    { id: 6, name: 'Meniul6',author:"author6",  img: "assets/images/6.jpg", url: "/home" },
    { id: 7, name: 'Meniul7',author:"author7",  img: "assets/images/7.jpg", url: "/home" },
  ];

  next() {
    console.log(this.start)
    if (this.start < this.myArr.length - 2) {
      this.start++;
    }
  }

  prev() {
    console.log(this.start)
    if (this.start > 1) {
      this.start--;
    } 
  }
}
