import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class PatientsQueueService {
    // Define the routes we are going to interact with
    private listUrl = config.api_path + '/patient/list/date/';
    private tailsUrl = config.api_path + '/tail/list';
    private listByProcedureUrl = config.api_path + '/patient/list/procedure/';
    private deleteUrl = config.api_path + '/patient/delete/';
    private updateActivityUrl = config.api_path + '/patient/talon/set/activity/';
    private updateActivityAllUrl = config.api_path + '/patient/talon/setall/activity/';
    private updateOutOfTurnUrl = config.api_path + '/patient/talon/set/outofturn/';
    private updateStatusUrl = config.api_path + '/patient/set/status/';
    private updateBalanceUrl = config.api_path + '/patient/update/balance/';

    constructor(private http: HttpClient) { }

    getAll(date: string) {
        return this.http.get<any[]>(this.listUrl + date).pipe(catchError(this.handleError));
    }

    getTails() {
        return this.http.get<any[]>(this.tailsUrl).pipe(catchError(this.handleError));
    }

    getAllByProcedure(id: number) {
        return this.http.get<any[]>(this.listByProcedureUrl + id).pipe(catchError(this.handleError));
    }

    updateActivity(id: string, value: string) {
        return this.http.get(this.updateActivityUrl + id + '/' + value).pipe(catchError(this.handleError));
    }

    updateActivityAll(id: string, value: string) {
        return this.http.get(this.updateActivityAllUrl + id + '/' + value).pipe(catchError(this.handleError));
    }

    updateOutOfTurn(id: string, value: boolean) {
        return this.http.get(this.updateOutOfTurnUrl + id + '/' + value).pipe(catchError(this.handleError));
    }

    updateStatus(id: string, value: string) {
        return this.http.get(this.updateStatusUrl + id + '/' + value).pipe(catchError(this.handleError));
    }

    updateBalance(id: string, value: string) {
        return this.http.get(this.updateBalanceUrl + id + '/' + value).pipe(catchError(this.handleError));
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
