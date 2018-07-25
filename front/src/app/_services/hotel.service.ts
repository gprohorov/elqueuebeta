import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Procedure } from '../_models/index';

@Injectable()
export class HotelService {
  private bookingUrl        = config.api_path + '/workplace/hotel/booking/';
  private koikaListUrl      = config.api_path + '/workplace/hotel/koika/list/';
  private createRecordUrl   = config.api_path + '/workplace/hotel/record/create/';

  constructor(private http: HttpClient) { }

  getBooking() {
      return this.http.get(this.bookingUrl).pipe(catchError(this.handleError));
  }

  getKoikaList() {
      return this.http.get(this.koikaListUrl).pipe(catchError(this.handleError));
  }
  
  createRecord(record: any) {
      return this.http.post(this.createRecordUrl, record).pipe(catchError(this.handleError));
  }
  
  // Implement a method to handle errors if any
  private handleError(err: HttpErrorResponse | any) {
    console.error('An error occurred', err);
    return Observable.throw(err.message || err);
  }

}
