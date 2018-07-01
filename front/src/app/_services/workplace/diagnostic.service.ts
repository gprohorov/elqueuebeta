import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../../config';

import { Patient } from '../../_models/index';

@Injectable()
export class WorkplaceDiagnosticService {
    // Define the routes we are going to interact with
    private getPatientUrl = config.api_path + '/workplace/diagnostic/get/';
    private startProcedureUrl = config.api_path + '/workplace/diagnostic/start/';
    private cancelProcedureUrl = config.api_path + '/workplace/diagnostic/cancel/';
    private executeProcedureUrl = config.api_path + '/workplace/diagnostic/execute/';

    constructor(private http: HttpClient) { }

    getPatient(patientId: string) {
        return this.http.get(this.getPatientUrl + patientId).pipe(catchError(this.handleError));
    }

    startProcedure(talonId: string) {
        return this.http.get(this.startProcedureUrl + talonId).pipe(catchError(this.handleError));
    }

    cancelProcedure(talonId: string) {
        return this.http.get(this.cancelProcedureUrl + talonId).pipe(catchError(this.handleError));
    }

    executeProcedure(talonId: string, therapy: any = {}) {
        return this.http.post(this.executeProcedureUrl + talonId, therapy)
            .pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
