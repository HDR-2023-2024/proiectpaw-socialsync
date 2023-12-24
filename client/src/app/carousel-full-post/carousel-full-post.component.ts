import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-carousel-full-post',
  templateUrl: './carousel-full-post.component.html',
  styleUrls: ['./carousel-full-post.component.css']
})
export class CarouselFullPostComponent {
  start = 0;
  numElementsToShow = 1;
  
  @Input() myArr: any[] = [];

  ngOnInit(){
   // console.log("Datele pozei: " + this.myArr[0] +"      " +  this.myArr[1]);
  }

  next() {
    console.log(this.start)
    if (this.start < this.myArr.length - 1) {
      this.start++;
    }
  }

  prev() {
    console.log(this.start)
    if (this.start > 0) {
      this.start--;
    } 
  }
}
