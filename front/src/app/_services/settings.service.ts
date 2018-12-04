import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class SettingsService {

    private getUrl = config.api_path + '/settings/get';
    private updateUrl = config.api_path + '/settings/update/';

    constructor(private http: HttpClient) { }
    
    get() {
        return this.http.get(this.getUrl).pipe(catchError(this.handleError));
    }
    
    update(data: any) {
        return this.http.post(this.updateUrl, data).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
