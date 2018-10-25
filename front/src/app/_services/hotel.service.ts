import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class HotelService {
    private koikaMapUrl = config.api_path + '/hotel/koika/map/';
    private koikaListUrl = config.api_path + '/hotel/koika/list/';
    private getRecordUrl = config.api_path + '/hotel/record/get/';
    private createRecordUrl = config.api_path + '/hotel/record/create/';
    private updateRecordUrl = config.api_path + '/hotel/record/update/';
    private cancelRecordUrl = config.api_path + '/hotel/record/cancel/';

    constructor(private http: HttpClient) { }

    getKoikaMap(start: string) {
        return this.http.get(this.koikaMapUrl + start).pipe(catchError(this.handleError));
    }

    getKoikaList() {
        return this.http.get(this.koikaListUrl).pipe(catchError(this.handleError));
    }

    getRecord(recordId: string) {
        return this.http.get(this.getRecordUrl + recordId).pipe(catchError(this.handleError));
    }

    createRecord(record: any) {
        return this.http.post(this.createRecordUrl, record).pipe(catchError(this.handleError));
    }

    updateRecord(record: any) {
        return this.http.post(this.updateRecordUrl, record).pipe(catchError(this.handleError));
    }

    cancelRecord(recordId: string) {
        return this.http.get(this.cancelRecordUrl + recordId).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
