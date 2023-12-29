

import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CreateTopicService } from '../create-topic.service';
import { CommunityService } from '../community.service';
import { HttpClient } from '@angular/common/http';
import { ViewChild, ElementRef } from '@angular/core';
import { StorageService } from '../storage.service';

interface FileObject {
  [key: string]: string;
}

@Component({
  selector: 'app-community-edit',
  templateUrl: './community-edit.component.html',
  styleUrls: ['./community-edit.component.css']
})


export class CommunityEditComponent {
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, private createTopic: CreateTopicService, private communityService: CommunityService, private storageService: StorageService) { }
  photoName: string = '';
  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;
  @Input() community: any = {
    id: '1',
    name: 'Sample Community',
    description: 'This is a sample community.',
    ageRestriction: true,
    photoId: 123,
    postIds: ['post1', 'post2'],
    creatorId: 'user123',
    memberIds: ['member1', 'member2', 'member3']
  };

  selectedPhoto: File | null = null;
  files: any[] = [];

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.community.id = params['topicId'];
    });
    this.loadPostDetails();
  }

  saveChanges() {
    console.log('Changes saved:', this.community, 'Selected photo:', this.selectedPhoto);
  }

  onPhotoChange(event: any) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedPhoto = fileList[0];
    }
  }

  loadPostDetails() {
    this.communityService.getCommunityById(this.community.id, '0').subscribe(
      (data) => {
        this.community = data;
        console.log(data)

        if (this.community.photoId && this.community.photoId.startsWith("http://localhost:8088/api/v1/storage/img/")) {
          this.community.photoId = this.community.photoId.slice("http://localhost:8088/api/v1/storage/img/".length);
        }
        console.log(this.community.photoId )
        // Suprascriem array-ul original cu noul array modificat
        this.storageService.getData({ "elements": [this.community.photoId] }).subscribe(
          (data) => {
            console.log(data)
            const transformedArray = data.map((obj: any) => {
              const uuid = Object.keys(obj)[0] as keyof FileObject;
              const name = obj[uuid];
              return {
                url: `http://localhost:8088/api/v1/storage/img/${uuid}`,
                name: name
              };
            });
            this.files = transformedArray;
            this.photoName = this.files[0].name;
            console.log(this.files);
          },
          (error) => {
            console.error('Eroare la incarcarea datelor:', error);
          }
        );
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

  uploadFiles() {
    const fileInput = this.fileInputRef.nativeElement;
    const files = fileInput.files;

    if (files && files.length > 0) {
      const formData = new FormData();

      for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
      }

      this.photoName = files[0].name;
      this.http.post<any>('http://localhost:8086/api/v1/storage/upload-multipartFile', formData)
        .subscribe(
          data => {
            console.log(data);
          },
          error => {
            this.router.navigate(['/internal-server-error']);
            console.error('Eroare:', error);
          }
        );
    } else {
      console.error('Niciun fisier. selectat.');
    }
  }
}
