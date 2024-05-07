import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  clientData = {
    name: 'John Doe',
    address: '123 Main St',
    city: 'San Francisco',
    state: 'CA',
    zip: '94105',
    transactions: [
      {date: '2020-01-01', amount: 100, description: 'Product A'},
      {date: '2020-01-02', amount: 200, description: 'Product B'},
      {date: '2020-01-03', amount: 300, description: 'Product C'},
      {date: '2020-01-04', amount: 400, description: 'Product D'},
      {date: '2020-01-05', amount: 500, description: 'Product E'},
      {date: '2020-01-06', amount: 600, description: 'Product F'},
    ]
  }
  totalSales = this.clientData.transactions.reduce((acc, curr) => acc + curr.amount, 0);
  constructor() { }
}
