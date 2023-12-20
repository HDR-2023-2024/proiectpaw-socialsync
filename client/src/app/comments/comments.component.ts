import { Component, Input } from '@angular/core';
import { CommentService } from '../comment.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';

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
    public authService: AuthService,
    private scrollService: ScroolServiceService
  ) { }



  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.handleScrollEnd();
      this.scrollService.getScrollObservable()
        .pipe(
          debounceTime(100),
          filter(() => this.scrollService.isScrolledToBottom())
        )
        .subscribe(() => {
          this.handleScrollEnd();
        });
    });
  }

  handleScrollEnd() {
    this.page++;
    console.log(this.page);
    let oldSize = this.comments.length;
    this.commentService.getComment(this.postId, this.page.toString()).subscribe(
      (data) => {
        console.log('Datele de la server:', data);

        for (const item of data) {
          this.comments.push(item);
        }
        var seenIds: Record<string, boolean> = {};
        var filteredArr = this.comments.filter(function (item: any) {
          if (seenIds.hasOwnProperty(item.id)) {
            return false;
          }
          seenIds[item.id] = true;
          return true;
        });
        this.comments = filteredArr;
        this.comments = this.comments.reverse();
        if (oldSize == this.comments.length) {
          this.page--;
          if (this.page < 0) {
            this.page = 0;
          }
        }
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
        this.ngOnInit();
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
        this.ngOnInit();

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
