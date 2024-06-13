import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, catchError, throwError } from "rxjs";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}  
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error) => {
        if (error.status === 401) {
          // Token expired, redirect to login page
          this.router.navigate(['/login']);
        }
        return throwError(error);
      })
    );
  }
}