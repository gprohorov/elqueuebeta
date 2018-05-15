import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class PatientsQueueService {
    // Define the routes we are going to interact with
    private listUrl = config.api_path + '/patient/list/today';
    private tailsUrl = config.api_path + '/tail/list';
    private listByProcedureUrl = config.api_path + '/patient/list/procedure/';
    private deleteUrl = config.api_path + '/patient/delete/';
    private updateActivityUrl = config.api_path + '/patient/update/activity/';
    private updateStatusUrl = config.api_path + '/patient/update/status/';
    private updateBalanceUrl = config.api_path + '/patient/update/balance/';

    private doctorPatientUrl = config.api_path + '/tail/first/';
    private startProcedureUrl = config.api_path + '/patient/start/procedure/';
    private cancelProcedureUrl = config.api_path + '/patient/cancel/procedure/';
    private executeProcedureUrl = config.api_path + '/patient/execute/procedure/';

    constructor(private http: HttpClient) { }

    getAll(search: string = '') {
        return this.http
            .get<any[]>(this.listUrl + search)
            .pipe(catchError(this.handleError));
    }

    getTails() {
        return this.http
            .get<any[]>(this.tailsUrl)
            .pipe(catchError(this.handleError));
    }

    getDoctorPatient(procedureID: number) {
        return this.http
            .get<any>(this.doctorPatientUrl + procedureID)
            .pipe(catchError(this.handleError));
    }

    getAllByProcedure(id: number) {
        return this.http
            .get<any[]>(this.listByProcedureUrl + id)
            .pipe(catchError(this.handleError));
    }

    updateActivity(id: string, value: string) {
        return this.http
            .get(this.updateActivityUrl + id + '/' + value)
            .pipe(catchError(this.handleError));
    }

    updateStatus(id: string, value: string) {
        return this.http
            .get(this.updateStatusUrl + id + '/' + value)
            .pipe(catchError(this.handleError));
    }

    updateBalance(id: string, value: string) {
        return this.http
            .get(this.updateBalanceUrl + id + '/' + value)
            .pipe(catchError(this.handleError));
    }

    startProcedure(patientID: string, procedureID: number) {
        return this.http
            .get(this.startProcedureUrl + patientID + '/' + procedureID)
            .pipe(catchError(this.handleError));
    }

    cancelProcedure(patientID: string, procedureID: number) {
        return this.http
            .get(this.cancelProcedureUrl + patientID + '/' + procedureID)
            .pipe(catchError(this.handleError));
    }

    executeProcedure(patientID: string, procedureID: number) {
        return this.http
            .get(this.executeProcedureUrl + patientID + '/' + procedureID)
            .pipe(catchError(this.handleError));
    }

    delete(id: string) {
        return this.http
            .get(this.deleteUrl + id)
            .pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
