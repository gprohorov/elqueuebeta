import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class StatisticService {
    // Define the routes we are going to interact with
    private generalStatisticsFromToUrl = config.api_path + '/statistics/general/list/';
    private cashSummaryUrl = config.api_path + '/statistics/report/current';
    private doctorsProceduresFromToUrl = config.api_path + '/statistics/doctors/';
    private proceduresStatistics = config.api_path + '/statistics/procedures/';
    private patientsDebetorsUrl = config.api_path + '/statistics/patients/debetors';
    private patientStatisticsUrl = config.api_path + '/statistics/patient/';

    constructor(private http: HttpClient) { }

    getGeneralStatisticsFromTo(start: Date, finish: Date) {
        return this.http.get(this.generalStatisticsFromToUrl
                + start.toISOString().split('T').shift()
                + '/'
                + finish.toISOString().split('T').shift()
            ).pipe(catchError(this.handleError));
    }

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

    getProceduresStatistics(start: Date, finish: Date) {
        return this.http.get(this.proceduresStatistics
            + start.toISOString().split('T').shift()
            + '/'
            + finish.toISOString().split('T').shift()
        ).pipe(catchError(this.handleError));
    }

    getProceduresStatisticsByDoctors(start: Date, finish: Date, procedureId: number) {
        return this.http.get(this.proceduresStatistics
            + [start.toISOString().split('T').shift(),
            finish.toISOString().split('T').shift(),
                procedureId].join('/')
        ).pipe(catchError(this.handleError));
    }

    getPatientsDebetors() {
        return this.http.get(this.patientsDebetorsUrl).pipe(catchError(this.handleError));
    }

    getPatientStatistics(patientId: string) {
        return this.http.get(this.patientStatisticsUrl + patientId).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
