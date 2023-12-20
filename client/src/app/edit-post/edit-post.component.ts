import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CreatePostService } from '../create-post.service';
import { FullPostService } from '../full-post.service';
import { StorageService } from '../storage.service';

interface FileObject {
  [key: string]: string;
}

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})



export class EditPostComponent {
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute, private createPost: CreatePostService, private postService: FullPostService, private storageService: StorageService) { }
  imageUrls: any[] = [];
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
    "timestampUpdated": 0,
    "topic": {
      "id": "",
      "title": "",
      "photoId": ""
    },
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.post.id = params['postId'];
    });
    this.loadPostDetails();
  }

  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;
  selectedPhoto: File | null = null;

  saveChanges() {
    this.post.photos = this.imageUrls;
    console.log(this.post);
    // 
    const transformedUrls: string[] = this.files.map((obj: any) => {
      return obj.url;
    });
    this.post.photos = transformedUrls;
    console.log("Poze: " + this.post.photos[0]);
    this.post.topicId = this.post.topic.id;
    this.createPost.updatePost(this.post);
  }

  onPhotoChange(event: any) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedPhoto = fileList[0];
    }
  }

  uploadFiles() {
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
            //console.log(data);
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
    } else {
      console.error('Niciun fisier. selectat.');
    }
  }

  loadPostDetails() {
    this.postService.getData(this.post.id, '0').subscribe(
      (data) => {
        this.post = data;
        // cerere pentru nume poze 
        // http://localhost:8088/api/v1/storage/img/
        const modifiedPhotos = this.post.photos.map((i: string) => {
          if (i.startsWith("http://localhost:8088/api/v1/storage/img/")) {
            return i.slice("http://localhost:8088/api/v1/storage/img/".length);
          }
          return i;
        });

        // Suprascriem array-ul original cu noul array modificat
        this.post.photos = modifiedPhotos;
        this.storageService.getData({ "elements": this.post.photos }).subscribe(
          (data) => {
            const transformedArray = data.map((obj: any) => {
              const uuid = Object.keys(obj)[0] as keyof FileObject;
              const name = obj[uuid];
              return {
                url: `http://localhost:8088/api/v1/storage/img/${uuid}`,
                name: name
              };
            });
            this.files = transformedArray;
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

  deleteFile(i: number) {
    this.files.splice(i, 1);
  }
}
