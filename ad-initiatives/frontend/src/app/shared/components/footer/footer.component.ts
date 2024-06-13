import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  userData: any[] = []; // Dummy data

  constructor() { }

  ngOnInit(): void {
    // Initialization code here
    this.userData.push({ username: 'UserSample', email: 'userSample@test.com' }); //Dummy data initialization
  }

  isLoggedIn(): boolean {
    return this.userData.length > 0; 
  }
}
