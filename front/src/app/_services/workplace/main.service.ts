import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../../config';

@Injectable()
export class WorkplaceMainService {
    // Define the routes we are going to interact with
    private getTailsUrl = config.api_path + '/workplace/tails/';

    constructor(private http: HttpClient) { }

    getTails() {
        return this.http.get(this.getTailsUrl).pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
