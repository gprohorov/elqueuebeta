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
    private salaryOnDayUrl = config.api_path + '/salary/date';
    private salarySummaryOnDayUrl = config.api_path + '/salary/daily';
    private getDoctorSalaryHistoryUrl = config.api_path + '/salary/list/payment';
    private getDoctorSalaryBaseUrl = config.api_path + '/salary/doctor';
    private getDoctorSalaryPreviewUrl = config.api_path + '/salary/doctor/preview';
    private saveDoctorSalaryPreviewUrl = config.api_path + '/salary/doctor/preview/save';
    private giveSalaryUrl = config.api_path + '/salary/get';
    private giveSalarySAUrl = config.api_path + '/salary/get-sa';
    private setSalaryUrl = config.api_path + '/salary/set';
    private kassaUrl = config.api_path + '/cashbox/kassa';
    private tozeroUrl = config.api_path + '/cashbox/tozero/';
    private kassaAddOutcomeUrl = config.api_path + '/cashbox/create';
    private kassaAddOutcomeSAUrl = config.api_path + '/cashbox/create-sa';

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
    
    getSalaryOnDay(date: string) {
        return this.http.get(this.salaryOnDayUrl + '/' + date).pipe(catchError(this.handleError));
    }
    
    getSalarySummaryOnDay(from: string, to: string) {
        return this.http.get(this.salarySummaryOnDayUrl + '/' + from + '/' + to)
            .pipe(catchError(this.handleError));
    }
    
    getDoctorSalaryHistory(doctor: number, from: string, to: string) {
        return this.http.get(this.getDoctorSalaryHistoryUrl
            + '/' + doctor + '/' + from + '/' + to).pipe(catchError(this.handleError));
    }
    
    getDoctorSalaryBase(doctor: number, from: string, to: string) {
        return this.http.get(this.getDoctorSalaryBaseUrl
            + '/' + doctor + '/' + from + '/' + to).pipe(catchError(this.handleError));
    }
    
    getDoctorSalaryPreview(data: any) {
        return this.http.post(this.getDoctorSalaryPreviewUrl, data).pipe(catchError(this.handleError));
    }
    
    saveDoctorSalaryPreview(data: any) {
        return this.http.post(this.saveDoctorSalaryPreviewUrl, data).pipe(catchError(this.handleError));
    }

    kassaAddOutcome(data: any, SA: boolean) {
        return this.http.post(SA ? this.kassaAddOutcomeSAUrl : this.kassaAddOutcomeUrl, data)
            .pipe(catchError(this.handleError));
    }

    giveSalary(data: any, SA: boolean) {
        return this.http.post(SA ? this.giveSalarySAUrl : this.giveSalaryUrl, data)
            .pipe(catchError(this.handleError));
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

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
