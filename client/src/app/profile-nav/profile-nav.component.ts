import { Component,ComponentFactoryResolver, Input } from '@angular/core';

@Component({
  selector: 'app-profile-nav',
  templateUrl: './profile-nav.component.html',
  styleUrls: ['./profile-nav.component.css']
})

export class ProfileNavComponent {
  activeTab: number = 0;

  setActiveTab(index: number) {
    this.activeTab = index;
  }

  @Input() navComponents: any[] = []
  @Input() userId: any = null

 
}
