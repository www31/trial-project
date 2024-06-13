import { Component, OnInit } from '@angular/core';
import { LoginComponent } from '../../shared/components/login/login.component';
import { CustomBottonComponent } from '../../shared/components/custom-button/custom-button.component';
import { LoginService } from '../../shared/components/login/login.services';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FooterComponent } from '../../shared/components/footer/footer.component';
import { LoginModel } from '../../models/login.model';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [LoginComponent, FooterComponent, CustomBottonComponent, HttpClientModule, CommonModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})

export class UserComponent implements OnInit{

  public errMessage: any;
  public response: any;
  loginObj: LoginModel = new LoginModel();

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.errMessage = "";
  }

  onUsernameChanged(username: string) {
    this.loginObj.username = username;
  }

  onPasswordChanged(password: string) {
    this.loginObj.password = password;
  }

  onLoginClick(): any {
    this.loginService.login(this.loginObj.username, this.loginObj.password)
      .subscribe((res: any) => {
        this.errMessage="";
        this.response = res;
        console.log(res, "<<<<<< RES")
      }, err => {
        if(err.error.message) {
          this.errMessage = String(err.error.message);
        } else {
          this.errMessage = String(err.message);
        }
        console.log(err, "<<<<< ERROR")
      });
  }
}