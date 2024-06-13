import { CommonModule } from '@angular/common';
import { Component, Input, OnInit  } from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-transaction-info-card',
  standalone: true,
  imports: [CommonModule, ChartModule],
  templateUrl: './transaction-info-card.component.html',
  styleUrl: './transaction-info-card.component.css'
})
export class TransactionInfoCardComponent implements OnInit{
  @Input() trainingInfo: any;

  public seeMore: boolean = false;
  expandedItemId: number | null = null;

  data: any;
  options: any;

  constructor() {}

  ngOnInit(): void {
    console.log(this.isArray(), "<<<<<<")
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

      this.options = {
        cutout: '65%',
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        }
    };
  }
 
  isArray(): boolean {
    return Array.isArray(this.trainingInfo);
  }

  statusColor(status: string) {
    switch (status) {
      case 'success':
        return {bgColor: 'var(--light-green)', borderColor: '1px solid var(--green)'};
      case 'In Progress':
        return {bgColor: 'var(--light-purple)', borderColor: '1px solid var(--purple)'};
      case 'Overdue':
        return {bgColor: 'var(--light-red)', borderColor: '1px solid var(--red)'};
      default:
        return {bgColor: 'var(--light-green)', borderColor: '1px solid var(--green)'};
    }
  }

  showMore(id: number) {
    if (id) {
      this.expandedItemId = this.expandedItemId === id ? null : id;
    }
  }

  expand(id: number) {
    return this.expandedItemId === id ? 'See less' : 'See more';
  }

  getChartDate(progress: number) {
    return this.data = {
      // labels: ['A', 'B', 'C'],
      datasets: [
          {
              data: [100 -progress, progress],
              backgroundColor: ['#d3d3d3', '#522A8B'],
              borderWidth: 1,
              // hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
          }
      ]
  };
  }
}
