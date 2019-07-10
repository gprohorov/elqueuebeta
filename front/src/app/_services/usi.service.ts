import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class UsiService {
  // Define the routes we are going to interact with
  private listUrl   = config.api_path + '/workplace/usi/patient/';
  private getUrl    = config.api_path + '/workplace/usi/get/';
  private createUrl = config.api_path + '/workplace/usi/create/';
  private updateUrl = config.api_path + '/workplace/usi/update/';
  private deleteUrl = config.api_path + '/workplace/usi/delete/';

  constructor(private http: HttpClient) { }

  getAllforPatient(patientId: string) {
    return this.http.get(this.listUrl + patientId).pipe(catchError(this.handleError));
  }

  get(id: string) {
    return this.http.get(this.getUrl + id).pipe(catchError(this.handleError));
  }

  create(data: any) {
    return this.http.post(this.createUrl, data).pipe(catchError(this.handleError));
  }

  update(data: any) {
    return this.http.post(this.updateUrl, data).pipe(catchError(this.handleError));
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
