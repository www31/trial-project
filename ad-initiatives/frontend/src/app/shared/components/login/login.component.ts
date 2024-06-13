import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Register } from '../../../models/register';
import { CustomBottonComponent } from '../custom-button/custom-button.component';
import { LoginService } from './login.services';
import { charLen, emailPattern, lowCase, matchpassword, minimuminput, oneDIgit, oneSymbol, upCase } from './matchpassword.validator';
import { PopupOptions } from './popup-options';
import { PopupService } from './popupl.service';
import { loginMessages } from '../../constants/login.constants';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CustomBottonComponent,
    FormsModule,
    CommonModule,
    ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @ViewChild(loginMessages.vcrPass, { static: true, read: ViewContainerRef })
  vcrPassword!: ViewContainerRef;
  @ViewChild(loginMessages.vcrCreatePassword, { static: true, read: ViewContainerRef })
  vcrCreatePassword!: ViewContainerRef;
  @ViewChild(loginMessages.vcrStatus, { static: true, read: ViewContainerRef })
  vcrStatus!: ViewContainerRef;
  @Output() emailChanged = new EventEmitter<string>();
  @Output() passwordChanged = new EventEmitter<string>();
  @Output() usernameChanged = new EventEmitter<string>();
  @Output() loginButtonClicked = new EventEmitter<void>();
  @Input() errMessage: string | undefined;
  @Input() loginData: any | undefined;
  buttonColor: string = loginMessages.buttonColor;
  buttonWidth: string = loginMessages.buttonWidth;
  buttonMargin: string = loginMessages.buttonMargin;
  buttonSize: string = loginMessages.buttonSize;
  visible: boolean = true;
  visibleConfirm: boolean = true;
  forgotPassword: string = loginMessages.emptyString;
  resetPassword: string = loginMessages.resetPW;
  createPassword: string = loginMessages.newPW;
  updatePassword: string = loginMessages.updPW;
  isUsernameInput: boolean = false;
  isPasswordInput: boolean = false;
  disableBtn: boolean = true;
  disableBtnChPW: boolean = true;
  secondForm: FormGroup;
  thirdForm: FormGroup;
  fourthForm: FormGroup;
  checkUNForm: FormGroup;
  userReg: Register = {
    email: loginMessages.emptyString,
    username: loginMessages.emptyString,
    password: loginMessages.emptyString,
  }; 
  popupTitle: string = loginMessages.emptyString;
  popupContent: string = loginMessages.emptyString;
  nameNotFound: string = loginMessages.emptyString;
  imgSuccess = loginMessages.imgSuccess;
  imgFail = loginMessages.imgFail;
  constructor(
    private popupService: PopupService,
    private loginService: LoginService,
    private router: Router
  ) {
    this.secondForm = new FormGroup({
      RegisterEmail: new FormControl(null, [Validators.required])
    }, {
      validators: emailPattern
    });
    this.thirdForm = new FormGroup({
      NewPassword: new FormControl(null, [Validators.required]),
      ConfirmPassword: new FormControl(null, [Validators.required])
    }, {
      validators: [
        matchpassword,
        minimuminput,
        lowCase,
        upCase,
        oneDIgit,
        oneSymbol,
        charLen
      ]
    });
    this.fourthForm = new FormGroup({
      NewPassword: new FormControl(null, [Validators.required]),
      ConfirmPassword: new FormControl(null, [Validators.required])
    }, {
      validators: [
        matchpassword,
        minimuminput,
        lowCase,
        upCase,
        oneDIgit,
        oneSymbol,
        charLen
      ]
    });
    this.checkUNForm = new FormGroup({
      UserName: new FormControl(null, [Validators.required]),
    });
  }

  onEmailChange(email: Event) {
    const target = email.target as HTMLInputElement;
    this.emailChanged.emit(target.value);
  }

  onPasswordChange(password: Event) {
    const target = password.target as HTMLInputElement;
    if (!target.value) {
      this.isPasswordInput = false;
    } else {
      this.isPasswordInput = true;
    }
    this.onInputValidate();
    // target.value='Password@1234'
    this.passwordChanged.emit(target.value);
  }

  onUsernameChange(username: Event) {
    const target = username.target as HTMLInputElement;
    if (!target.value) {
      this.isUsernameInput = false;
    } else {
      this.isUsernameInput = true;
    }
    this.onInputValidate();
    // target.value = 'Username'
    this.usernameChanged.emit(target.value);
  }

  onInputValidate() {
    if (!this.isUsernameInput || !this.isPasswordInput) {
      this.disableBtn = true;
    } else {
      this.disableBtn = false;
    }
  }

  onUsernameChPW(username: Event) {
    const target = username.target as HTMLInputElement;
    if (!target.value.length) {
      this.disableBtnChPW = true;
    } else {
      this.disableBtnChPW = false;
    }
  }

  openLogin() {
    this.loginButtonClicked.emit();
    // First, checks if it isn't implemented yet.
    setTimeout(() => {
      if(this.loginData!= null) {
        let userName = this.loginData.data.userName;
        this.loginService.storeUsername(userName);
        if (!this.loginData.data.isActive) {
          this.popupTitle = loginMessages.verifyHeader;
          this.popupContent = loginMessages.verifyContent1.concat(userName, loginMessages.verifyContent2);
        }        
        else if (this.loginData.data.updatedDate == null) {
          this.forgotPassword = this.updatePassword;
        } else {
          this.loginService.storeRefreshToken();
          this.loginService.storeLogInDetails(
            this.loginData.data.userId,
            this.loginData.data.token,
            this.loginData.data.accessName
          )
          this.redirectAfterSuccess();
        }
      }
    }, 1000)

  }

  afterSuccess() {
    return this.popupTitle == loginMessages.successUp;
  }

  redirectAfterSuccess() {
    if(this.loginData!= null) {
      this.loginService.refreshHome(this.loginData.data.accessName);
    }
  }

  loginPage() {
    this.loginService.refreshPage(loginMessages.loginRoute);
  }

  showPW() {
    this.visible = !this.visible;
  }

  changeVisibility(): string {
    return this.visible ? loginMessages.imgHideTxt : loginMessages.imgShowTxt;
  }

  changeType(): string {
    return this.visible ? loginMessages.password : loginMessages.text;
  }

  showPWConfirm() {
    this.visibleConfirm = !this.visibleConfirm;
  }

  changeVisibilityConfirm(): string {
    return this.visibleConfirm ? loginMessages.imgHideTxt : loginMessages.imgShowTxt;
  }

  changeTypeConfirm(): string {
    return this.visibleConfirm ? loginMessages.password : loginMessages.text;
  }

  openPopupTemplate1(view: TemplateRef<Element>) {
    this.popupService.open(this.vcrPassword, view, {
      animations: {
        popup: {
          enter: loginMessages.enterSlide,
        },
        overlay: {
          enter: loginMessages.enterFade,
          leave: loginMessages.leaveFade,
        },
      },
      size: {
        width: loginMessages.popupWidthSmall,
      },
    });
  }

  options: PopupOptions = {
    animations: {
      popup: {
        enter: loginMessages.enterSlide,
      },
      overlay: {
        enter: loginMessages.enterFade,
        leave: loginMessages.leaveFade,
      },
    },
    size: {
      width: loginMessages.popupWidthLarge,
    }
  }

  openPopupTemplate2(view: TemplateRef<Element>) {
    // email format: username@lenovo.com
    const characters = loginMessages.characters;
    function generateString(length: number) {
      let result = ' ';
      const charactersLength = characters.length;
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
      }
      return result.trim();
    }
    let userEmail = this.secondForm.get(loginMessages.registerEmail)?.value;
    let userName = userEmail.match(loginMessages.userEmailPattern)[0];
    this.userReg = {
      email: userEmail,
      username: userName,
      password: generateString(10),
    }
    this.loginService.addUserLogin(this.userReg).subscribe(
      (res: any) => {
        console.log(res)
        this.popupTitle = res.status.charAt(0).toUpperCase() + res.status.slice(1).toLowerCase();
        if (res.status==loginMessages.errorDown) {
          this.popupContent = res.message;
        } else {
          this.popupContent = loginMessages.regUserSuccess;
        }
        setTimeout(() => {
          this.popupService.open(this.vcrStatus, view, this.options);
        }, 1000);
      },
      (error: HttpErrorResponse) => {
        if(error.error != null) {
          let errors = error.error.errors[0]
          this.popupContent = String(errors.message);
          this.errMessage = this.popupContent;
        } else {
          this.popupContent = loginMessages.duplicateExist;
          this.errMessage = this.popupContent;
        }
        this.popupTitle = loginMessages.errorUp;
        this.userReg.password = loginMessages.emptyString;
        setTimeout(() => {
          this.popupService.open(this.vcrStatus, view, this.options);
        }, 1000);
      }
    )
  }

  openPopupTemplate3_1(view: TemplateRef<Element>) {
    this.close();
    this.popupService.open(this.vcrCreatePassword, view, this.options);
  }

  openPopupTemplate3_2(view: TemplateRef<Element>) {
    let confirmPass = this.thirdForm.get(loginMessages.confirmPassword)?.value;
    let uName = this.loginService.getUsername();
    this.loginService.changePassword(uName!, confirmPass).subscribe(
      (res: any) => {
        this.popupTitle = loginMessages.successUp;
        this.popupContent = loginMessages.passChgSuccess;
        console.log(loginMessages.consoleRes, res);
      },
      (error: HttpErrorResponse) => {
        this.popupTitle = loginMessages.errorUp;
        this.popupContent = loginMessages.passChgFail;
        if(error.error != null) {
          this.popupContent = error.error.errors[0].message;
        }
        console.log(loginMessages.consoleErr, error);
      }
    )
    this.popupService.open(this.vcrStatus, view, this.options);
  }

  openPopupTemplate4(view: TemplateRef<Element>) {
    let confirmPass = this.fourthForm.get(loginMessages.confirmPassword)?.value;
    let uName = this.loginService.getUsername();
    this.loginService.changePassword(uName!, confirmPass).subscribe(
      (res: any) => {
        this.popupTitle = loginMessages.successUp;
        this.popupContent = loginMessages.passUpdSuccess;
      },
      (error: HttpErrorResponse) => {
        this.popupTitle = loginMessages.errorUp;
        this.popupContent = loginMessages.passUpdFail;
      }
    )
    this.popupService.open(this.vcrStatus, view, this.options);
  }

  close() {
    this.popupService.close();
  }

  openResetPass() {
    this.forgotPassword = this.resetPassword;
    this.close();
  }

  openCreatePass() {
    this.forgotPassword = this.createPassword;
    this.close();
  }

  openUserNameInput() {
    let uName = this.checkUNForm.get(loginMessages.userName)?.value
    this.loginService.deleteUsername();
    this.loginService.isExistUsername(uName).subscribe((res:any) => {
      console.log(loginMessages.consoleRes, res);
      let status = res.status;
      this.nameNotFound = res.message;
      if(status == loginMessages.successDown) {
        this.openCreatePass();
        this.loginService.storeUsername(uName);      
      }
    }, (err: any) => {
      let e = err.error;
      console.log(loginMessages.consoleErr, e);
      this.nameNotFound = e.errors[0].message;
    });
  }

  onInputEmailMismatchForm2 = () => {
    let secondFormError = this.secondForm.errors?.[loginMessages.emailerror];
    if(secondFormError) {
      this.errMessage = loginMessages.mustEndWith;
    } else {
      this.errMessage = loginMessages.emptyString;
    }
    return secondFormError
  }

  onInputNotMatchForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.passwordmatcherror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputMinCharsForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.charlengtherror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputNoUpcaseForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.caseuperror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputNoLowCaseForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.caselowerror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputNoNumForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.digiterror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputNoSymbolForm3 = () => {
    let thirdFormError = this.thirdForm.errors?.[loginMessages.symbolerror];
    let boolMatch = this.thirdForm.untouched
    return thirdFormError || boolMatch
  }

  onInputNotMatchForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.passwordmatcherror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  onInputMinCharsForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.charlengtherror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  onInputNoUpcaseForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.caseuperror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  onInputNoLowCaseForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.caselowerror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  onInputNoNumForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.digiterror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  onInputNoSymbolForm4 = () => {
    let fourthFormError = this.fourthForm.errors?.[loginMessages.symbolerror];
    let boolMatch = this.fourthForm.untouched
    return fourthFormError || boolMatch
  }

  resetPass() {
    console.log(this.secondForm.value)
  }

  createPass() {
    console.log(this.thirdForm.value)
  }

  updatePass() {
    console.log(this.fourthForm.value)
  }

  searchUsername() {
    console.log(this.checkUNForm.value)
  }
}