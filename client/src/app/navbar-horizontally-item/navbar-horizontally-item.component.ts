import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-navbar-horizontally-item',
  templateUrl: './navbar-horizontally-item.component.html',
  styleUrls: ['./navbar-horizontally-item.component.css']
})
export class NavbarHorizontallyItemComponent {
  @Input() data: any;

  isVisible = false;

  toggleTopics() {
    this.isVisible = !this.isVisible;
  }
}
