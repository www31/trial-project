import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrl: './create-employee.component.css'
})
export class CreateEmployeeComponent{
  
  employee: Employee = new Employee();
  constructor(private employeeService: EmployeeService,
    private router: Router) { }

  // ngOnInit(): void {
    // throw new Error('Method not implemented.');
  // }

  saveEmployee() {
    this.employeeService.createEmployee(this.employee).subscribe( data => {
      console.log('saveEmp',data);
      this.goToEmployeeList();
    },
    error => console.log(error));
  }

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }

  onSubmit() {
    console.log('onSub',this.employee);
    this.saveEmployee();
  }

}
