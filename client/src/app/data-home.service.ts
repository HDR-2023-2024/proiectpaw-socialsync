import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataHomeService {

  constructor(private http: HttpClient) { }




  getData(apiUrl: string): Observable<any> {
    return this.http.get(apiUrl);
  }


  async getDataSync(apiUrl : string): Promise<any> {
    //console.log(page)
      try {
        const data = await this.http.get(apiUrl).toPromise();
        console.log(apiUrl);
        return data;
      } catch (error) {
        console.error('Eroare la obtinerea datelor:', error);
        throw error;
      }
  }

  getDataTopics(apiUrl: string): Observable<any> {
    return this.http.get(apiUrl);
  }


  async getDataSyncTopics(apiUrl: string): Promise<any> {
    //console.log(page)
      try {
        const data = await this.http.get(apiUrl).toPromise();
        console.log(apiUrl);
        return data;
      } catch (error) {
        console.error('Eroare la obtinerea datelor:', error);
        throw error;
      }
  }
}
