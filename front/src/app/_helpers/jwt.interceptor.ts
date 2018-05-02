import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  // constructor(private token: TokenStorage, private router: Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
//    let authReq = request;
//    if (this.token.getToken() != null) {
//      authReq = request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.token.getToken())});
//    }
    if (currentUser && currentUser.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.token}`
        }
      });
    }
    return next.handle(request);
//    return next.handle(authReq).do(
//      (err: any) => {
//        if (err instanceof HttpErrorResponse) {
//          if (err.status === 401) {
//            this.router.navigate(['login']);
//          }
//        }
//      }
//    );
  }
}
