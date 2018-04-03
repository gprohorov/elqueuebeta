import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class PatientsQueueService {
  // Define the routes we are going to interact with
  private listUrl           = config.api_path + '/patient/list/';
  private deleteUrl         = config.api_path + '/patient/delete/';

  constructor(private http: HttpClient) { }

  getAll(search: string = '') {
    return this.http
      .get<any[]>(this.listUrl + search)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  delete(id: number) {
     return this.http
      .get(this.deleteUrl + id)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  // Implement a method to handle errors if any
  private handleError(err: HttpErrorResponse | any) {
    console.error('An error occurred', err);
    return Observable.throw(err.message || err);
  }

}
