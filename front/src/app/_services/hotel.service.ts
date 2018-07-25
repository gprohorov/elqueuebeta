import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Procedure } from '../_models/index';

@Injectable()
export class HotelService {
  private listUrl   = config.api_path + '/workplace/hotel/koika/list/';

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.listUrl).pipe(catchError(this.handleError));
  }

  // Implement a method to handle errors if any
  private handleError(err: HttpErrorResponse | any) {
    console.error('An error occurred', err);
    return Observable.throw(err.message || err);
  }

}
