import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';

@Component({
  selector: 'app-full-post',
  templateUrl: './full-post.component.html',
  styleUrls: ['./full-post.component.css']
})
export class FullPostComponent {

  @Input() data: any;
  private page = 0;
  postId: string = '';

  constructor(private route: ActivatedRoute, private postService: FullPostService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadPostDetails(); 
      console.log(this.postId);
    });
  }

  loadPostDetails() {
    this.postService.getData(this.postId, this.page.toString()).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.data = data;
        console.log("hei");
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }
  
  convertTimestampToDateTime(timestamp:number) {
    var date = new Date(timestamp * 1000); 
    return date.toLocaleString();
  }
  
}
