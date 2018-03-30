import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';

import { Person } from '../_models/index';

@Injectable()
export class PersonService {
  // Define the routes we are going to interact with
  private getPersonListUrl = 'http://192.168.1.2:8088/api/person/list';
  private deletePersontUrl = 'http://192.168.1.2:8088/api/person/delete/';

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http
      .get<Person[]>(this.getPersonListUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  deletePerson(id: number) {
    return this.http
      .get(this.getPersonListUrl + id)
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