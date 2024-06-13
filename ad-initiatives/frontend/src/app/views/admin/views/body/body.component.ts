import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-body',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './body.component.html',
  styleUrl: './body.component.css'
})
export class BodyComponent {


  // getBodyClass(): string {
  //   let styleClass = '';
  //   if(this.collapsed && this.screenWidth > 768){
  //     styleClass = 'body-trimmed';
  //   } else if (this.collapsed && this.screenWidth <=  768 && this.screenWidth > 0) {
  //     styleClass = 'body-md-screen';
  //   }
  //   return styleClass;
  // }
}
