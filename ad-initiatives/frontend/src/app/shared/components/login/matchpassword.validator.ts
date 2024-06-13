import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const matchpassword: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  if(password?.value != confirmpassword?.value) {
    return {
      passwordmatcherror : true
    }
  }
  return null;
};

export const minimuminput: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('^(?=.*[A-Z])');
  let confrimPw = confirmpassword?.value?.match('^(?=.*[A-Z])');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      inputminerror : true
    }
  }
  return null;
};

export const lowCase: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('(?=.*[a-z])');
  let confrimPw = confirmpassword?.value?.match('(?=.*[a-z])');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      caselowerror : true
    }
  }
  return null;
};
export const upCase: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('^(?=.*[A-Z])');
  let confrimPw = confirmpassword?.value?.match('^(?=.*[A-Z])');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      caseuperror : true
    }
  }
  return null;
};
export const oneDIgit: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('(.*[0-9].*)');
  let confrimPw = confirmpassword?.value?.match('(.*[0-9].*)');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      digiterror : true
    }
  }
  return null;
};
export const oneSymbol: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('(?=.*[!@#$%^&*])');
  let confrimPw = confirmpassword?.value?.match('(?=.*[!@#$%^&*])');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      symbolerror : true
    }
  }
  return null;
};
export const charLen: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let password = control.get('NewPassword');
  let confirmpassword = control.get('ConfirmPassword');
  let newPw = password?.value?.match('.{8,}');
  let confrimPw = confirmpassword?.value?.match('.{8,}');
  let bothMatch  = !(newPw && confrimPw)
  if(bothMatch) {
    return {
      charlengtherror : true
    }
  }
  return null;
};

export const emailPattern: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  
  let userEmail = control.get('RegisterEmail');
  let match = userEmail?.value?.match('[A-za-z]+@[Ll]enovo\.com');
  if(!match) {
    return {
      emailerror : true
    }
  }
  return null;
};