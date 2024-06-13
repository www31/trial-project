import { Component } from '@angular/core';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { CustomBottonComponent } from '../../../../shared/components/custom-button/custom-button.component';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CustomBottonComponent,PanelModule,ChartModule,CardModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  public trainingProgressData = {
    labels: ["Completed Training", "Ongoing Training", "Delayed Training", "Not yet started", "For Certification", "Certified"],
    datasets: [
      {
        data: [150, 50, 100, 10, 30, 100],
        backgroundColor: ["green",
          "yellow",
          "red",
          "#c0b9b9",
          "Blue",
          "#522A8B"],
      },
    ],
  };

  public activeVsBudgetData = {
    datasets: [
      {
        data: [70,30],
        backgroundColor: ["#522A8B",
          "#c0b9b9"],
      },
    ],
  };

  public certVsApproved = {
    datasets: [
      {
        data: [80,20],
        backgroundColor: ["#522A8B",
          "#c0b9b9"],
      },
    ],
  };

  chartOptions = {
    plugins: {
      legend: {
        labels: {
          color: "#495057",
        },
      },
    },
  };

  basicData = {
    labels: ['Sunday', 'Monday', 'Tuesday', 'Wednesday',
      'Thursday', 'Friday', 'Saturday'],
    datasets: [
      {
        backgroundColor: 'violet',
        data: [66, 49, 81, 71, 26, 65, 60]
      }
    ]
  };

  StackedOptions = { 
    indexAxis: 'x', 
    scales: { 
        x: { 
            ticks: { 
                color: '#black'
            }, 
            grid: { 
                color: 'rgba(255,255,255,0.2)'
            } 
        }, 
        y: { 
            ticks: { 
                color: '#black'
            }, 
            grid: { 
                color: 'rgba(255,255,255,0.2)'
            } 
        } 
    } 
}; 
}
