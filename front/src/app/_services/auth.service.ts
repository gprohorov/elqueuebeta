import { config } from '../../config';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { NgxPermissionsService } from 'ngx-permissions';

import { TokenStorage, UserStorage } from '../_storage/index';

@Injectable()
export class AuthService {

    private authUrl = config.api_path + '/authenticate';

    constructor(
        private http: HttpClient,
        private token: TokenStorage,
        private user: UserStorage,
        private permissionsService: NgxPermissionsService
    ) { }

    attemptAuth(username: string, password: string): Observable<any> {
        const credentials = { username: username, password: password };
        return this.http.post(this.authUrl, credentials).pipe(
          catchError(this.handleError)
        );
    }

    setAuth(data): void {
        this.token.saveToken(data.token);
        this.user.saveUser(data);
        this.permissionsService.loadPermissions(data.authorities);
    }

    deAuth(): void {
        this.token.signOut();
        this.user.signOut();
        this.permissionsService.flushPermissions();
    }

    getUserInfo() {
        return this.user.getUser();
    }

    isAuth() {
        return (this.token.getToken()) ? true : false;
    }

    isAdmin() {
        const user: any = this.user.getUser();
        return user.authorities.indexOf('ROLE_ADMIN') > -1;
    }

    isSuperadmin() {
        const user: any = this.user.getUser();
        return user.authorities.indexOf('ROLE_SUPERADMIN') > -1;
    }

    // Implement a method to handle errors if any
    private handleError(err: HttpErrorResponse | any) {
        console.error('An error occurred', err);
        return Observable.throw(err.message || err);
    }
}

