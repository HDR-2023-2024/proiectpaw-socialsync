import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CreatePostService } from '../create-post.service';
import { StorageService } from '../storage.service';

interface InputData {
  stringName: string;
}

interface FileObject {
  [key: string]: string;
}

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent {
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, private createPost: CreatePostService, private storageService: StorageService) { }
  imageUrls: any[] = [];
  @Input() inputData: InputData | null = null; // in ce caut
  files: any[] = [];

  post: any = {

    "id": -1,
    "creatorId": -1,
    "topicId": -1,
    "title": '',
    "content": '',
    "upvotes": [],
    "downvotes": [],
    "photos": [],
    "score": 0,
    "timestampCreated": 0,
    "timestampUpdated": 0
  }

  topicId: string | null = null;
  topicName: string | null = null;

  ngOnInit() {
    this.route.params.subscribe(params => {
      //  { path: "create-post/:topicId/:topicName", component: CreatePostComponent }
      this.topicId = params['topicId'];
      this.topicName = params['topicName'];
    });
  }

  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;
  selectedPhoto: File | null = null;

  saveChanges() {
    this.post.photos = this.imageUrls;
    this.post.topicId = this.topicId;
    //console.log(this.post);
    const transformedUrls: string[] = this.files.map((obj: any) => {
      return obj.url;
    });
    this.post.photos = transformedUrls;
    this.createPost.addPost(this.post)
  }

  onPhotoChange(event: any) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedPhoto = fileList[0];
    }
  }

  uploadFiles() {
    //console.log("Apel incarcare poza!");
    const fileInput = this.fileInputRef.nativeElement;
    const files = fileInput.files;

    if (files && files.length > 0) {
      const formData = new FormData();

      for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
      }

      this.http.post<any>('http://localhost:8086/api/v1/storage/upload-multipartFile', formData)
        .subscribe(
          (data: any[]) => {
            console.log(data);
            if (data && data.length > 0) {
              this.imageUrls = data.map(item => item.url);
              const modifiedPhotos = this.imageUrls.map((i: string) => {
                if (i.startsWith("http://localhost:8088/api/v1/storage/img/")) {
                  return i.slice("http://localhost:8088/api/v1/storage/img/".length);
                }
                return i;
              });
              this.storageService.getData({ "elements": modifiedPhotos }).subscribe(
                (data) => {
                  const transformedArray = data.map((obj: any) => {
                    const uuid = Object.keys(obj)[0] as keyof FileObject;
                    const name = obj[uuid];
                    return {
                      url: `http://localhost:8088/api/v1/storage/img/${uuid}`,
                      name: name
                    };
                  });

                  transformedArray.forEach((url: string) => {
                    this.files.push(url);
                  });

             
                },
                (error) => {
                  console.error('Eroare la incarcarea datelor:', error);
                }
              );

            }
          },
          error => {
            console.error('Eroare:', error);
          }
        );
    }
  }

  deleteFile(i: number) {
    this.files.splice(i, 1);
  }
}


