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
    private getPatientUrl = config.api_path + '/workplace/first/';
    private setReadyUrl = config.api_path + '/workplace/setready/';
    private startProcedureUrl = config.api_path + '/workplace/start/';
    private cancelProcedureUrl = config.api_path + '/workplace/cancel/';
    private executeProcedureUrl = config.api_path + '/workplace/execute/';

    constructor(private http: HttpClient) { }

    getPatient(procedureId: number) {
        return this.http.get(this.getPatientUrl + procedureId)
            .pipe(catchError(this.handleError));
    }

    setReady(procedureId: number) {
        return this.http.get(this.setReadyUrl + procedureId)
            .pipe(catchError(this.handleError));
    }

    startProcedure(patientID: string, procedureID: number) {
        return this.http
            .get(this.startProcedureUrl + patientID + '/' + procedureID)
            .pipe(catchError(this.handleError));
    }

    cancelProcedure(patientID: string) {
        return this.http
            .post(this.cancelProcedureUrl + patientID, {})
            .pipe(catchError(this.handleError));
    }

    executeProcedure(patientID: string) {
        return this.http
            .post(this.executeProcedureUrl + patientID, {})
            .pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
