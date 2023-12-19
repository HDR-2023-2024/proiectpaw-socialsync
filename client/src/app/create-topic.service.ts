import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CreateTopicService {
  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient, private router: Router) { }
  private loginUrl = 'http://localhost:8086/api/v1/topics';

  saveTopic(name: string, description: string, photoId: string) {
    const postData = {
      id: -1,
      name: name,
      description: description,
      photoId: photoId,
      creatorId: -1,
      timestampCreated: 0,
      timestampUpdated: 0
    };

    const token = this.storage.get('Token');

    const headers = new HttpHeaders({
      'Authorization': token
    });

    return this.http.post<any>("http://localhost:8086/api/v1/topics", postData, { headers: headers }).subscribe(
      data => {
        //console.log(data);
        this.router.navigate(['/unauthorized']);
        this.router.navigate(['community/', data.id]);
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 401) {
          this.router.navigate(['/unauthorized']);
        }
      }
    );
  }
}
