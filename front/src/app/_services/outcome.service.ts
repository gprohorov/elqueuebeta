import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/throw';
import { config } from '../../config';

@Injectable()
export class OutcomeService {

    private getOutcomeTreeUrl = config.api_path + '/outcome/gettree';
    private getOutcomeTreeSumUrl = config.api_path + '/outcome/gettree-sum/';
    private getItemListUrl = config.api_path + '/outcome/list/';
    private createNodeUrl = config.api_path + '/outcome/createnode/';
    private updateNodeUrl = config.api_path + '/outcome/updatenode/';
    private deleteNodeUrl = config.api_path + '/outcome/deletenode/';

    constructor(private http: HttpClient) { }
    
    getOutcomeTree() {
        return this.http.get(this.getOutcomeTreeUrl).pipe(catchError(this.handleError));
    }
    
    getOutcomeTreeSum(from: string, to: string) {
        return this.http.get(this.getOutcomeTreeSumUrl + from + '/' + to)
            .pipe(catchError(this.handleError));
    }
    
    sendNode(node: any) {
        const url = (node.id != null) ? this.updateNodeUrl : this.createNodeUrl; 
        return this.http.post(url, node).pipe(catchError(this.handleError));
    }
    
    deleteNode(id: string) {
        return this.http.get(this.deleteNodeUrl + id).pipe(catchError(this.handleError));
    }
    
    getItemList(id: string, from: string, to: string) {
        return this.http.get(this.getItemListUrl + id + '/' + from + '/' + to)
            .pipe(catchError(this.handleError));
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}
