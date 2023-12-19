import { Component } from '@angular/core';
import { ViewChild, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateTopicService } from '../create-topic.service';

@Component({
  selector: 'app-community-create',
  templateUrl: './community-create.component.html',
  styleUrls: ['./community-create.component.css']
})
export class CommunityCreateComponent {
  constructor(private http: HttpClient, private createTopic: CreateTopicService) { }
  imageUrl: string | null = null;

  community = {
    name: '',
    description: '',
    photoId: ''
  }

  ngOnInit() {
    console.log("Ceva");
  }


  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;
  selectedPhoto: File | null = null;

  saveChanges() {
    this.community.photoId = this.imageUrl != null ? this.imageUrl : '';
    console.log('Changes saved:', this.community, 'Selected photo:', this.selectedPhoto);
    this.createTopic.saveTopic(this.community.name, this.community.description, this.community.photoId);
  }

  onPhotoChange(event: any) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedPhoto = fileList[0];
    }
  }

  uploadFiles() {
    console.log("Apel incarcare poza!");
    const fileInput = this.fileInputRef.nativeElement;
    const files = fileInput.files;

    if (files && files.length > 0) {
      const formData = new FormData();

      for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
      }

      this.http.post<any>('http://localhost:8086/api/v1/storage/upload-multipartFile', formData)
        .subscribe(
          data => {
            console.log(data);
            if (data && data.length > 0) {
              this.imageUrl = data[0].url;
            }
          },
          error => {
            console.error('Eroare:', error);
          }
        );
    } else {
      console.error('Niciun fisier. selectat.');
    }
  }
}
