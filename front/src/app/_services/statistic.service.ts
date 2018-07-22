import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class StatisticService {
    // Define the routes we are going to interact with
    private cashSummaryUrl = config.api_path + '/statistics/report/current';
    private doctorsProceduresFromToUrl = config.api_path + '/statistics/doctors/';
    private patientsDebetorsUrl = config.api_path + '/statistics/patients/debetors';

    constructor(private http: HttpClient) { }

    getCashSummary() {
        return this.http.get(this.cashSummaryUrl).pipe(catchError(this.handleError));
    }

    getDoctorsProceduresFromTo(start: Date, finish: Date) {
        return this.http.get(this.doctorsProceduresFromToUrl
            + start.toISOString().split('T').shift()
            + '/'
            + finish.toISOString().split('T').shift()
        ).pipe(catchError(this.handleError));
    }

    getPatientsDebetors() {
        return this.http.get(this.patientsDebetorsUrl).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
