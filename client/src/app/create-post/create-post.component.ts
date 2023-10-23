import { Component } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';


@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent {
  editorConfig = {
    editable: true,
    spellcheck: true,
    height: 'auto',
    minHeight: '300px',
    placeholder: 'Enter text here...',
    translate: 'no'
  }
}