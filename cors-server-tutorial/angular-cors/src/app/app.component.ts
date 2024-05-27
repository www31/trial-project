import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit{
  title = 'angular-cors';
  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getCandy();
  }
  getCandy() {
    this.http.get('http://localhost:5000/candy').subscribe(data => {
      console.log(data)
    })
  }
}
