import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

import { Patient } from '../_models/index';

@Injectable()
export class PatientService {
    // Define the routes we are going to interact with
    private listUrl = config.api_path + '/patient/list/';
    private getUrl = config.api_path + '/patient/get/';
    private saveUrl = config.api_path + '/patient/save/';
    private createUrl = config.api_path + '/patient/new/';
    private deleteUrl = config.api_path + '/patient/delete/';
    private assignProcedureUrl = config.api_path + '/patient/create/activetalon/';
    private assignProceduresOnDateUrl = config.api_path + '/patient/create/talons/';
    private accountingUrl = config.api_path + '/accounting/create';
    private getBalanceUrl = config.api_path + '/patient/balance/today/';
    private receiptUrl = config.api_path + '/patient/create/receipt/';
    private checkUrl = config.api_path + '/patient/create/receipt/';

    constructor(private http: HttpClient) { }

    getAll(search: string = '') {
        return this.http.get<Patient[]>(this.listUrl + search).pipe(catchError(this.handleError));
    }

    get(id: string) {
        return this.http.get<Patient>(this.getUrl + id).pipe(catchError(this.handleError));
    }

    update(model: Patient) {
        return this.http.post(this.saveUrl, model).pipe(catchError(this.handleError));
    }

    create(model: any) {
        return this.http.post(this.createUrl, model).pipe(catchError(this.handleError));
    }

    delete(id: number) {
        return this.http.get(this.deleteUrl + id).pipe(catchError(this.handleError));
    }

    assignProcedure(patientId: string, procedureId: number, date: string, appointed: number,
        activate: boolean, schema?: boolean) {
        return this.http.get(this.assignProcedureUrl
            + [patientId, procedureId, date, appointed, activate || false, schema || false].join('/'))
            .pipe(catchError(this.handleError));
    }

    assignProceduresOnDate(patientId: string, date: string, appointed: number) {
        return this.http.get(this.assignProceduresOnDateUrl + patientId + '/' + date + '/' + appointed)
            .pipe(catchError(this.handleError));
    }

    accounting(data: any) {
        return this.http.post(this.accountingUrl, data).pipe(catchError(this.handleError));
    }

    getBalance(patientId: string) {
        return this.http.get(this.getBalanceUrl + patientId).pipe(catchError(this.handleError));
    }

    getReceipt(patientId: string, from: string, to: string) {
        return this.http.get(this.receiptUrl + patientId + '/' + from + '/' + to)
        .pipe(catchError(this.handleError));
    }

    getCheck(patientId: string) {
        return this.http.get(this.checkUrl + patientId + '/today')
        .pipe(catchError(this.handleError));
    }

    sortBy(criteria: PatientSearchCriteria, list) {
        return list.sort((a, b) => {
            let x = a.person[criteria.sortColumn], y = b.person[criteria.sortColumn];
            if (x && x.toLowerCase) x = x.toLowerCase();
            if (y && y.toLowerCase) y = y.toLowerCase();
            if (criteria.sortDirection === 'asc') {
                if (x < y) return -1;
                if (x > y) return 1;
                return 0;
            } else {
                if (x > y) return -1;
                if (x < y) return 1;
                return 0;
            }
        });
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}

export class PatientSearchCriteria {
    sortColumn: string;
    sortDirection: string;
}

