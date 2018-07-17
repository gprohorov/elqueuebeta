import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../../config';

import { Patient } from '../../_models/index';

@Injectable()
export class WorkplaceCommonService {
    // Define the routes we are going to interact with
    private getPatientUrl = config.api_path + '/workplace/patient/';
    private commentProcedureUrl = config.api_path + '/workplace/comment/';
    private startProcedureUrl = config.api_path + '/workplace/start/';
    private cancelProcedureUrl = config.api_path + '/workplace/cancel/';
    private executeProcedureUrl = config.api_path + '/workplace/execute/';
    private zoneSubUrl = config.api_path + '/workplace/subzone/';
    private zoneAddUrl = config.api_path + '/workplace/addzone/';

    constructor(private http: HttpClient) { }

    getPatient(patientId: string, procedureId: number) {
        return this.http.get(this.getPatientUrl + patientId + '/' + procedureId)
            .pipe(catchError(this.handleError));
    }

    commentProcedure(talonId: string, comment: string) {
        return this.http.post(this.commentProcedureUrl + talonId, comment)
            .pipe(catchError(this.handleError));
    }

    subZone(talonId: string) {
        return this.http.get(this.zoneSubUrl + talonId).pipe(catchError(this.handleError));
    }

    addZone(talonId: string) {
        return this.http.get(this.zoneAddUrl + talonId).pipe(catchError(this.handleError));
    }
    
    startProcedure(talonId: string) {
        return this.http.get(this.startProcedureUrl + talonId).pipe(catchError(this.handleError));
    }

    cancelProcedure(talonId: string) {
        return this.http.get(this.cancelProcedureUrl + talonId).pipe(catchError(this.handleError));
    }

    executeProcedure(talonId: string) {
        return this.http.get(this.executeProcedureUrl + talonId).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }

}
