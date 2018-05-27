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
    private getTailsUrl = config.api_path + '/workplace/tails/';
    private getPatientUrl = config.api_path + '/workplace/patient/';
    private commentProcedureUrl = config.api_path + '/workplace/comment/';
    private startProcedureUrl = config.api_path + '/workplace/start/';
    private cancelProcedureUrl = config.api_path + '/workplace/cancel/';
    private executeProcedureUrl = config.api_path + '/workplace/execute/';

    constructor(private http: HttpClient) { }

    getTails() {
        return this.http.get(this.getTailsUrl).pipe(catchError(this.handleError));
    }

    getPatient(talonId: string) {
        return this.http.get(this.getPatientUrl + talonId).pipe(catchError(this.handleError));
    }

    commentProcedure(talonId: string, comment: string) {
        return this.http.post(this.commentProcedureUrl + talonId, comment)
            .pipe(catchError(this.handleError));
    }

    startProcedure(talonId: string) {
        return this.http.get(this.startProcedureUrl + talonId)
            .pipe(catchError(this.handleError));
    }

    cancelProcedure(talonId: string) {
        return this.http.get(this.cancelProcedureUrl + talonId)
            .pipe(catchError(this.handleError));
    }

    executeProcedure(talonId: string, zones: number = 1) {
        return this.http.get(this.executeProcedureUrl + talonId + '/' + zones)
            .pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
