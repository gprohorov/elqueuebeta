import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class UserService {
  // Define the routes we are going to interact with
  private listUrl   = config.api_path + '/user/list';
  private getUrl    = config.api_path + '/user/get/';
  private updateUrl = config.api_path + '/user/update/';
  private deleteUrl = config.api_path + '/user/delete/';

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.listUrl).pipe(catchError(this.handleError));
  }

  get(id: string) {
    return this.http.get(this.getUrl + id).pipe(catchError(this.handleError));
  }

  update(model: any) {
    return this.http.post(this.updateUrl, model).pipe(catchError(this.handleError));
  }

  delete(id: string) {
    return this.http.get(this.deleteUrl + id).pipe(catchError(this.handleError));
  }

  // Implement a method to handle errors if any
  private handleError(err: HttpErrorResponse | any) {
    console.error('An error occurred', err);
    return Observable.throw(err.message || err);
  }
}
