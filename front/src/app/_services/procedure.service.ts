import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Procedure } from '../_models/index';

@Injectable()
export class ProcedureService {
  // Define the routes we are going to interact with
  private listUrl   = config.api_path + '/procedure/list/';
  private getUrl    = config.api_path + '/procedure/get/';
  private updateUrl = config.api_path + '/procedure/update/';
  private deleteUrl = config.api_path + '/procedure/delete/';

  constructor(private http: HttpClient) { }

  getAll(search: string = '') {
    return this.http
      .get<Procedure[]>(this.listUrl + search)
      .pipe(
        catchError(this.handleError)
      );
  }

  get(id: number) {
    return this.http
      .get<Procedure>(this.getUrl + id)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(model: Procedure) {
    return this.http
      .post(this.updateUrl, model)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  delete(id: number) {
    return this.http
      .post(this.deleteUrl + id, {})
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
