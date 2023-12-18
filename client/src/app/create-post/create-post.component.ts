import { Component } from '@angular/core';
import { CreatePostService } from '../create-post.service';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { Input } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent {
  
  @Input() data: any;
  form?: NgForm;

  editorConfig:AngularEditorConfig = {
      editable: true,
      spellcheck: true,
      height: '15rem',
      minHeight: '5rem',
      placeholder: 'Enter text here...',
      translate: 'no',
      customClasses: [
        {
          name: "quote",
          class: "quote",
        },
        {
          name: 'redText',
          class: 'redText'
        },
        {
          name: "titleText",
          class: "titleText",
          tag: "h1",
        },
      ]
  }

  title: string = '';
  content: string = '';
  url:string=' ';
  photo:string=' ';

  constructor(private createPostService: CreatePostService, private router:Router) {}

  createPost(): void {
    this.createPostService.addPost(this.title, this.content, this.photo).subscribe(
      (response) => {
          console.log('Postare creata cu succes.', response);
          if (response instanceof HttpResponse) {
            if (response.status === 201) {  
              this.router.navigate(['/full-post', response.body.id]); 
              console.log(response);
            }
          }
      },
      (error) => {
          console.error('Eroare la creare.', error);
      }
    ); 
  }

  clearForm(form: NgForm): void {
    form.resetForm();
  }
  
}