import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataHomeService {

  constructor(private http: HttpClient) {}
  
  
  

  getData(page : string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/posts?page=' + String(page));
  }

  
  async getDataSync(page: string): Promise<any> {
    const apiUrl = 'http://localhost:8086/api/v1/query/posts?page=' + page;
    console.log("&");
    try {
      const data = await this.http.get(apiUrl).toPromise();
      console.log("*");
       console.log(apiUrl);
      return data;
    } catch (error) {
      console.error('Eroare la obtinerea datelor:', error);
      throw error;
    }
  }
}
