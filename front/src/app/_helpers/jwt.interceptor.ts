﻿import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpSentEvent,
  HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpUserEvent,
  HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/do';

import { TokenStorage } from '../_storage/token.storage';
import {HttpEvent} from '@angular/common/http';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private token: TokenStorage, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  // add authorization header with jwt token if available
  let authReq = req;
  if (this.token.getToken() != null) {
    authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.token.getToken())});
  }
  return next.handle(authReq).do(
    ( err: any ) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          this.router.navigate(['login', this.router.url]);
        }
      }
    }
  );
}
}
