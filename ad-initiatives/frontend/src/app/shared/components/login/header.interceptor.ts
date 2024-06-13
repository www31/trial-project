import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "./login.services";

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {
  loginService = inject(LoginService)
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const jwtToken = getJwtToken();
    const refresh = this.loginService.getRefreshToken();
    const expire = this.loginService.isTokenExpired();
    const newrequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${expire? refresh: jwtToken}`
      }
    });
    return next.handle(newrequest);
  }
}

function getJwtToken(): string | null {
  return localStorage.getItem('JWT_TOKEN');
}