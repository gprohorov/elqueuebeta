import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class PatientsQueueService {
  // Define the routes we are going to interact with
  private listUrl             = config.api_path + '/patient/list/';
  private listByProcedureUrl  = config.api_path + '/patient/list/procedure/';
  private deleteUrl           = config.api_path + '/patient/delete/';
  private updateActivityUrl   = config.api_path + '/patient/update/activity/';
  private updateStatusUrl     = config.api_path + '/patient/update/status/';
  private updateBalanceUrl    = config.api_path + '/patient/update/balance/';

  constructor(private http: HttpClient) { }

  getAll(search: string = '') {
    return this.http
      .get<any[]>(this.listUrl + search)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  getAllByProcedure(id: number) {
    return this.http
      .get<any[]>(this.listByProcedureUrl + id)
      .pipe(
        catchError(this.handleError)
      );
  }

  updateActivity(id: number, value: string) {
    return this.http
      .get(this.updateActivityUrl + id + '/' + value)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  updateStatus(id: number, value: string) {
    return this.http
      .get(this.updateStatusUrl + id + '/' + value)
      .pipe(
        catchError(this.handleError)
      );
  }  

  updateBalance(id: number, value: string) {
    return this.http
      .get(this.updateBalanceUrl + id + '/' + value)
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
