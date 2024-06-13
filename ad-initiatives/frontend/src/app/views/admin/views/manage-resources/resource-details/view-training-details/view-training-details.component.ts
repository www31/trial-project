import { Component, OnInit } from '@angular/core';
import { TransactionInfoCardComponent } from '../../../../../../shared/components/transaction-info-card/transaction-info-card.component';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AccordionComponent } from '../../../../../../shared/components/accordion/accordion.component';
import { ManageResourcesService } from '../../../../../../service/manage-resources.service';

@Component({
  selector: 'app-view-training-details',
  standalone: true,
  imports: [TransactionInfoCardComponent, CommonModule, AccordionComponent
  ],
  templateUrl: './view-training-details.component.html',
  styleUrl: './view-training-details.component.css'
})
export class ViewTrainingDetailsComponent {

  // public training?: TrainingDetails[];
  // public trnngCmpltdFilter?: TrainingDetails[];
  // public trnngInProgFilter?: TrainingDetails[];
  // public trnngOverdueFilter?: TrainingDetails[];
  // public trnngPendingFilter?: TrainingDetails[];

  public training: any[] = [];
  public trnngCmpltdFilter: any[] = [];
  public trnngInProgFilter: any[] = [];
  public trnngOverdueFilter: any[] = [];
  public trnngPendingFilter: any[] = [];

  public trnngPending: any[] = [];
  public links: { label: string, url: string }[] = [];

  public inProgHeader = ['In progress:'];
  public completedHeader = ['Completed Training:'];
  public overdueHeader = ['Overdue Training:'];
  public pendingHeader = ['Pending Training:'];

  constructor(private manageResourcesService: ManageResourcesService) { }

  ngOnInit() {
    this.getAllTrainings();
  }

  public getAllTrainings(): void {
    this.manageResourcesService.getTrainingList().subscribe(
      (res: any) => {
        this.training = res;
        console.log('training', res);
        const completedTraining = res.data.filter((item: any) => item.status === 'Completed');
        const inProgTraining = res.data.filter((item: any) => item.status === 'In Progress');
        const overdueTraining = res.data.filter((item: any) => item.status === 'Overdue');
        const pendingTraining = res.data.filter((item: any) => item.status === 'Pending');
        this.trnngCmpltdFilter = completedTraining;
        this.trnngInProgFilter = inProgTraining;
        this.trnngOverdueFilter = overdueTraining;
        this.trnngPendingFilter = pendingTraining;

        const pendingLinks = pendingTraining.map((item: any) => ({
          label: item.description,
          url: item.link
        }));
        this.trnngPending = pendingLinks;
        this.links = [...this.links, ...pendingLinks];

      },
      (error: HttpErrorResponse) => {
        console.log('no trainings', error);
      }
    )
  }


}