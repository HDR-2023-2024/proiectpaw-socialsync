import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-short-post',
  templateUrl: './short-post.component.html',
  styleUrls: ['./short-post.component.css']
})
export class ShortPostComponent {
  @Input() data: any;
}
