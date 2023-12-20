import { Component, Input } from '@angular/core';
import { CommentService } from '../comment.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent {
 
  @Input() comments: any[] = [];
  @Input() replies: any[] = [];
  private page: number = 0;
  postId: string = '';
  showReplyTextarea: boolean[] = [];

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    private fb: FormBuilder,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadComments();
    });
  }

  loadComments() {
    this.commentService.getComment(this.postId, this.page.toString()).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.comments = data;
      },
      (error) => {
        console.error('Eroare la incarcarea comentariilor:', error);
      }
    );
  }

  addComment(content: string) {
    this.commentService.postComment(this.postId, content).subscribe(
      (response) => {
        console.log('Comentariu creat cu succes.', response);
        console.log(content);
        this.comments = [response, ...this.comments];
      },
      (error) => {
        console.error('Eroare la creare.', error);
      }
    ); 
  }

  toggleReplyTextarea(index: number) {
    this.showReplyTextarea[index] = !this.showReplyTextarea[index];
  }

  
  addReplies(content: string) {
    this.commentService.postComment(this.postId, content).subscribe(
      (response) => {
        console.log('Comentariu creat cu succes.', response);
        this.replies = [response, ...this.comments];
      },
      (error) => {
        console.error('Eroare la creare.', error);
      }
    ); 
  }

   
  convertTimestampToDateTime(timestamp: number) {
    var date = new Date(timestamp * 1000);
    return date.toLocaleString();
  }
}
