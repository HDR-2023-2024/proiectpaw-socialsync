import { Component, Input } from '@angular/core';
import { CommentService } from '../comment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { PopupServiceService } from '../popup-service.service';

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
    private scrollService: ScroolServiceService,
    private router: Router,
    private popupService: PopupServiceService
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

  // noile comentarii cand e scrool la final
  handleScrollEnd() {
    this.page++;
    //console.log(this.page);
    let oldSize = this.comments.length; // daca raman pe pagina 0 
    this.commentService.getComment(this.postId, this.page.toString()).subscribe(
      (data) => {
        //console.log('Datele de la server:', data);
        for (const item of data) {
          this.comments.push(item);
        }
        var seenIds: Record<string, boolean> = {};
        // filtrare duplicate
        var filteredArr = this.comments.filter(function (item: any) {
          if (seenIds.hasOwnProperty(item.id)) {
            return false;
          }
          seenIds[item.id] = true;
          return true;
        });
        this.comments = filteredArr;
        //this.comments = this.comments.reverse();
        if (oldSize == this.comments.length) {
          this.page--;
          if (this.page < 0) {
            this.page = 0;
          }
        }
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la incarcarea comentariilor:', error);
      }
    );
  }

  addComment(content: string) {
    this.commentService.postComment(this.postId, content).subscribe(
      (response) => {
        //console.log('Comentariu creat cu succes.', response);
        this.ngOnInit();
        this.popupService.showPopup("Comentariu creat cu succes.");
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la creare.', error);
      }
    );
  }

  toggleReplyTextarea(index: number) {
    this.showReplyTextarea[index] = !this.showReplyTextarea[index];
  }

  convertTimestampToDateTime(timestamp: number) {
    var date = new Date(timestamp * 1000);
    return date.toLocaleString();
  }

  deleteComment(id : string){
    this.commentService.deleteComm(id);
    const index = this.comments.findIndex(comment => comment.id === id);
    if (index !== -1) {
      this.comments.splice(index, 1);
      this.popupService.showPopup("Comentariu șters cu succes!");
    }
  }

  editComment(commentId: number,editedComment : string){
  
    console.log(editedComment);
    this.commentService.updateComment(String(commentId), editedComment).subscribe(
      (response) => {
        console.log("Comentariu modificat cu succs")
        this.ngOnInit();
      },
      (error) => {
        this.router.navigate(['/internal-server-error'])
        console.error('Eroare la creare.', error);
      }
    );
  }
}
