import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { matchpassword } from './matchpassword.validator';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Password Match Validator';

  loginForm: FormGroup;

  constructor() {

    this.loginForm = new FormGroup({
      Email: new FormControl(null, [Validators.required]),
      Password: new FormControl(null, [Validators.required]),
      ConfirmPassword: new FormControl(null)
    },
      {
        validators: matchpassword
      });
  }

  login() {
    console.log(this.loginForm.value);
  }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
