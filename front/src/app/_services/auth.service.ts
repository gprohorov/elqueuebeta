import { config } from '../../config';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { TokenStorage } from "../_storage/index";

@Injectable()
export class AuthService {

  private authUrl = config.api_path + '/authenticate';

  constructor( private http: HttpClient, private token: TokenStorage ) {
  }

  attemptAuth( username: string, password: string ): Observable<any> {
    const credentials = {username: username, password: password};
    return this.http.post(this.authUrl, credentials);
  }

  deAuth(): void {
    this.token.signOut();
  }

  isAuth() {
    return (this.token.getToken()) ? true : false;
  }

}
