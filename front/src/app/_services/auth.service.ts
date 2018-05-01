import { config } from '../../config';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class AuthService {

  private authUrl = config.api_path + '/user/login/';

  constructor(private http: HttpClient) {  }

  attemptAuth(ussername: string, password: string) {
    const credentials = {username: ussername, password: password};
    return this.http.post(this.authUrl, credentials);
  }

}
