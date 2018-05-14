import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Patient } from '../_models/index';

@Injectable()
export class DoctorInterfaceService {
  // Define the routes we are going to interact with
  private getPatientUrl    = config.api_path + '/doctor/get/';
  private listUrl          = config.api_path + '/doctor/list/';
  private updateParientUrl = config.api_path + '/doctor/update/';

  constructor(private http: HttpClient) { }

  getPatient() {
    return this.http
      .get<Patient>(this.getPatientUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  updateParient() {
    return this.http
      .post(this.updateParientUrl, {})
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
