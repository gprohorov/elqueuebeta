import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
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
    private deleteUrl = config.api_path + '/patient/delete/';
    private saveUrl = config.api_path + '/patient/save/';
    private toPatientTodayUrl = config.api_path + '/patient/topatient/';

    constructor(private http: HttpClient) { }

    getAll(search: string = '') {
        return this.http
            .get<Patient[]>(this.listUrl + search)
            .pipe(catchError(this.handleError));
    }

    get(id: string) {
        return this.http
            .get<Patient>(this.getUrl + id)
            .pipe(catchError(this.handleError));
    }

    delete(id: string) {
        return this.http
            .get(this.deleteUrl + id)
            .pipe(catchError(this.handleError));
    }

    update(model: Patient) {
        return this.http
            .post(this.saveUrl, model)
            .pipe(catchError(this.handleError));
    }

    toPatientToday(id: string) {
        return this.http
            .get(this.toPatientTodayUrl + id)
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