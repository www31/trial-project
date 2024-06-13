import { Component, Input, } from '@angular/core';
import { UserInfoService } from '../../../../../../service/user-info.service';
import { CommonModule } from '@angular/common';
import { CustomBottonComponent } from '../../../../../../shared/components/custom-button/custom-button.component';


@Component({
  selector: 'app-view-personal-info',
  standalone: true,
  imports: [CommonModule, CustomBottonComponent],
  templateUrl: './view-personal-info.component.html',
  styleUrl: './view-personal-info.component.css',
})
export class ViewPersonalInfoComponent  {

  employeeInfo: any = {};
  @Input() memberDtl: any = {};
  public errMessage: any;


  constructor(
    private userInfoService: UserInfoService,
  ) { }

  ngOnInit(): void {
    this.getUserInfo();
    console.log('MemberId:', this.memberDtl.memberId)
  }

  getUserInfo() {
    this.userInfoService.getProfileInformation(this.memberDtl.memberId)
    .subscribe((res: any) => {
      this.errMessage="";
      this.employeeInfo = res.data;
      console.log('Personal Information:',this.employeeInfo)
    }, (err: any) => {
      this.errMessage = err.error;
      console.log(err, "<<<<< ERROR")
    });
  }
  
}