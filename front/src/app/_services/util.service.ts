import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class UtilService {
  // Define the routes we are going to interact with
  private resetDBURL = config.api_path + '/util/reset-db';
  private Task1URL = config.api_path + '/util/taskone';
  private Task2URL = config.api_path + '/util/tasktwo';

  constructor(private http: HttpClient) { }

  resetDB() {
    console.log('reset DB...', this.resetDBURL);
    return this.http
      .get(this.resetDBURL)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  Task1() {
    console.log('Task1...', this.Task1URL);
    return this.http
      .get(this.Task1URL)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  Task2() {
    console.log('Task2...', this.Task2URL);
    return this.http
      .get(this.Task2URL)
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
