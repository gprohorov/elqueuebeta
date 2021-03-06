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
    private patientsDebetorsExtUrl = config.api_path + '/statistics/patients/debetors-ext/';
    private patientStatisticsUrl = config.api_path + '/statistics/patient/';
    private patientsStatisticsUrl = config.api_path + '/statistics/patient/list/';
    private doctorStatisticsUrl = config.api_path + '/statistics/doctors/current/';
    
    private workWeekUrl = config.api_path + '/workweek/get/list/';
    private workMonthUrl = config.api_path + '/workmonth/get/list/';

    constructor(private http: HttpClient) { }

    getGeneralStatisticsFromTo(start: string, finish: string) {
        return this.http.get(this.generalStatisticsFromToUrl + start + '/' + finish).pipe(catchError(this.handleError));
    }

    getCashSummary() {
        return this.http.get(this.cashSummaryUrl).pipe(catchError(this.handleError));
    }

    getDoctorsProceduresFromTo(start: string, finish: string) {
        return this.http.get(this.doctorsProceduresFromToUrl + start + '/' + finish
        ).pipe(catchError(this.handleError));
    }

    getProceduresStatistics(start: string, finish: string) {
        return this.http.get(this.proceduresStatistics + start + '/' + finish)
            .pipe(catchError(this.handleError));
    }

    getProceduresStatisticsByDoctors(start: string, finish: string, procedureId: number) {
        return this.http.get(this.proceduresStatistics + start + '/' + finish + '/' + procedureId
        ).pipe(catchError(this.handleError));
    }

    getPatientsDebetors() {
        return this.http.get(this.patientsDebetorsUrl).pipe(catchError(this.handleError));
    }

    getPatientsDebetorsExt(start: string, finish: string) {
        return this.http.get(this.patientsDebetorsExtUrl + start + '/' + finish)
            .pipe(catchError(this.handleError));
    }

    getPatientStatistics(patientId: string) {
        const start = new Date().toISOString().split('T').shift();
        const finish = new Date().toISOString().split('T').shift();
        return this.http.get(this.patientStatisticsUrl + patientId + '/' + start + '/' + finish)
            .pipe(catchError(this.handleError));
    }


    getPatientsStatistics(start: string, finish: string) {
        return this.http.get(this.patientsStatisticsUrl + start + '/' + finish)
        .pipe(catchError(this.handleError));
    }

    getDoctorsCurrentStatistics(date: string) {
        return this.http.get(this.doctorStatisticsUrl + date).pipe(catchError(this.handleError));
    }
    
    
    showAllForYearByWeek(year: number) {
        return this.http.get(this.workWeekUrl + year).pipe(catchError(this.handleError));
    }
    
    showAllForYearByMonth(year: number) {
        return this.http.get(this.workMonthUrl + year).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
