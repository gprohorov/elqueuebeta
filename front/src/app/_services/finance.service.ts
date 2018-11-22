import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class FinanceService {
    // Define the routes we are going to interact with
    private salaryUrl = config.api_path + '/salary/list';
    private salarySummaryUrl = config.api_path + '/salary/list/summary';
    private getDoctorSalaryHistoryUrl = config.api_path + '/salary/list/payment';
    private giveSalaryUrl = config.api_path + '/salary/get';
    private setSalaryUrl = config.api_path + '/salary/set';
    private kassaUrl = config.api_path + '/cashbox/kassa';
    private tozeroUrl = config.api_path + '/cashbox/tozero/';
    private kassaAddOutcomeUrl = config.api_path + '/cashbox/create';
    private kassaOutcomeUrl = config.api_path + '/cashbox/current/details';

    constructor(private http: HttpClient) { }

    getSalary(week: number = null) {
        let url = this.salaryUrl;
        if (week !== null) url += '/' + week;
        return this.http.get(url).pipe(catchError(this.handleError));
    }
    
    getSalarySummary(from: string, to: string) {
        return this.http.get(this.salarySummaryUrl + '/' + from + '/' + to)
            .pipe(catchError(this.handleError));
    }
    
    getDoctorSalaryHistory(doctor: number, from: string, to: string) {
        return this.http.get(this.getDoctorSalaryHistoryUrl
            + '/' + doctor + '/' + from + '/' + to).pipe(catchError(this.handleError));
    }

    giveSalary(data: any) {
        return this.http.post(this.giveSalaryUrl, data).pipe(catchError(this.handleError));
    }
    
    setSalary(data: any) {
        return this.http.post(this.setSalaryUrl, data).pipe(catchError(this.handleError));
    }
    
    getKassa() {
        return this.http.get(this.kassaUrl).pipe(catchError(this.handleError));
    }
    
    toZero(sum: number) {
        return this.http.get(this.tozeroUrl + sum).pipe(catchError(this.handleError));
    }

    kassaAddOutcome(data: any) {
        return this.http.post(this.kassaAddOutcomeUrl, data).pipe(catchError(this.handleError));
    }
    
    getKassaOutcome() {
        return this.http.get(this.kassaOutcomeUrl).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
