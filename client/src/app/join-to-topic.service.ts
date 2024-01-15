import { Injectable ,Inject} from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class JoinToTopicService {

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,private http: HttpClient, private router: Router) { }

  async join(topic: string) {
    const token = this.storage.get('Token');
    const headers = new HttpHeaders({
      'Authorization': token
    });
  
    try {
      console.log(headers.getAll('Authorization'));
  

      const data = await this.http.put<any>("http://localhost:8086/api/v1/topics/" + topic + "/join", {}, { headers }).toPromise();
  

      console.log(data);

  
    } catch (error) {
      console.error('Eroare:', error);
  
      if (error instanceof HttpErrorResponse && error.status == 401) {
        this.router.navigate(['/unauthorized']);
      }
    }
  }
  
}
