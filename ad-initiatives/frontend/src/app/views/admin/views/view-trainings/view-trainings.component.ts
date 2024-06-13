import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CustomBottonComponent } from './../../../../shared/components/custom-button/custom-button.component';
import {  ManageTrainingsComponent } from '../manage-trainings/manage-trainings.component';

@Component({
  selector: 'view-trainings',
  standalone: true,
  imports: [ CommonModule,CustomBottonComponent],
  templateUrl: './view-trainings.component.html',
  styleUrl: './view-trainings.component.css' 
 
})

export class ViewTrainingsComponent implements OnInit{
    @Input() isVisible?: boolean;
    @Input() data: any;
    @Output() close = new EventEmitter<void>();   
    @Output() toggleShowManageTraining = new EventEmitter<void>();   
    @Input() showViewManage: boolean = false;
    @Output() showViewManageChange = new EventEmitter<boolean>();  


    back: string  = 'Back'
    constructor(private router: Router) { }
  
    ngOnInit(): void {      
      console.log(this.data);
    }     
    
    onClickToggleB(){
      this.showViewManage = !this.showViewManage;
      this.showViewManageChange.emit(this.showViewManage);
    }

  

  }