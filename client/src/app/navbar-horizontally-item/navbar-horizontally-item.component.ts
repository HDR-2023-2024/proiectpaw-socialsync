import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-horizontally-item',
  templateUrl: './navbar-horizontally-item.component.html',
  styleUrls: ['./navbar-horizontally-item.component.css']
})
export class NavbarHorizontallyItemComponent {
  constructor(private router: Router) {}
  @Input() data: any;

  isVisible = false;

  toggleTopics() {
    this.isVisible = !this.isVisible;
  }

  handleItemClick(item: string): void {
    console.log(`Item clicked: ${item}`);
    let modificat = item.replace("ă","a").replace("ț","t").replace("â","a").replace("ș","s").replace("Î","I").replace(" ","_").replace("Ț","T").replace("-","_");
    this.router.navigate(['/view-topics', { category: modificat }]);
  }
}
