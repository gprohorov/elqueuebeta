import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Person } from '../_models/index';

@Injectable()
export class PersonService {
  // Define the routes we are going to interact with
  private listUrl           = config.api_path + '/person/list/';
  private getUrl            = config.api_path + '/person/get/';
  private deleteUrl         = config.api_path + '/person/delete/';
  private updateUrl         = config.api_path + '/person/update/';
  private toPatientTodayUrl = config.api_path + '/person/topatient/';

  constructor(private http: HttpClient) { }

  getAll(search: string = '') {
    return this.http
      .get<Person[]>(this.listUrl + search)
      .pipe(
        catchError(this.handleError)
      );
  }

  get(id: number) {
    return this.http
      .get<Person>(this.getUrl + id)
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

  update(model: Person) {
    return this.http
      .post(this.updateUrl, model)
      .pipe(
        catchError(this.handleError)
      );
  }

  toPatientToday(id: number) {
    return this.http
      .get(this.toPatientTodayUrl + id)
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
