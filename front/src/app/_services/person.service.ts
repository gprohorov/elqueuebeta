import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';

import { Person } from '../_models/index';
import { config } from '../../config';

@Injectable()
export class PersonService {
  // Define the routes we are going to interact with
  private getPersonListUrl = config.api_path + '/person/list/';
  private getPersonUrl = config.api_path + '/person/get/';
  private deletePersonUrl = config.api_path + '/person/delete/';
  private createPersonUrl = config.api_path + '/person/create';
  private updatePersonUrl = config.api_path + '/person/update/';

  constructor(private http: HttpClient) { }

  getAll(search: string = '') {
    return this.http
      .get<Person[]>(this.getPersonListUrl + search)
      .pipe(
        catchError(this.handleError)
      );
  }

  getPerson(id: number) {
    return this.http
      .get<Person>(this.getPersonUrl + id)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  deletePerson(id: number) {
    return this.http
      .get(this.deletePersonUrl + id)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  createPerson(model: Person) {
    return this.http
      .post(this.createPersonUrl, model)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  updatePerson(model: Person) {
    return this.http
      .get('path here...')
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
