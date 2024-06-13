import { Component, Input, Output, EventEmitter, HostListener, ElementRef, QueryList, ViewChildren, OnInit } from '@angular/core';
import { AddTrainingModel } from '../../../../models/addtrainingmodel';
import { TrainingLinksModel } from '../../../../models/training-links-model';
import { AddEditTrainingComponent } from './add-edit-training';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManageTrainingService } from '../../../../service/manage-training.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-popup',
  imports: [AddEditTrainingComponent, CommonModule, FormsModule],
  standalone: true,
  templateUrl: './add-more-training-popup.html',
  styleUrls: ['./add-more-training-popup.css']
})
export class PopupComponent implements OnInit{
  @Input() isAddMorePopupVisible: boolean = false;
  @Output() save = new EventEmitter<AddTrainingModel>();
  @Output() close = new EventEmitter<TrainingLinksModel>();
  @Input() trainingDetails !: AddTrainingModel;
  @Input() textBoxes: TrainingLinksModel[] = [];
  @Output() trainingLinksListsChange = new EventEmitter<TrainingLinksModel[]>();

  constructor(private manageTrainingService: ManageTrainingService, private httpClient: HttpClient) { 
  }
  
  private savedState: { textBoxes: TrainingLinksModel[], trainingDetails: AddTrainingModel } = {
    textBoxes: [],
    trainingDetails: new AddTrainingModel()
  };

  ngOnInit() {
    this.initializeComponent();
  }

  private initializeComponent(): void {
    if (!this.trainingDetails.trainingLinksLists) {
      this.trainingDetails.trainingLinksLists = [];
    }

    if (this.savedState.textBoxes.length > 0) {
      this.textBoxes = [...this.savedState.textBoxes];
    } else if (this.textBoxes.length === 0) {
      this.addTextBox();
    }

    this.updateTrainingLinksLists();
    this.fetchTrainingLinks();
  }

  removeTextBox(index: number): void {
    this.textBoxes.splice(index, 1);
    this.emitTrainingLinksLists();
  }

  addTextBox(): void {
    const newTextBox: TrainingLinksModel = {
      trainingId: this.trainingDetails.id || '',
      subTrName: '',
      subTrLink: ''
    };
    this.textBoxes.push(newTextBox);
    this.emitTrainingLinksLists();
  }

  closePopup(): void {
    this.saveState();
    console.log('Training Links Array:', this.trainingDetails.trainingLinksLists);
    this.emitTrainingLinksLists(); // Ensure the latest data is emitted before closing
    this.isAddMorePopupVisible = false;
    console.log('Training Details After Popup Closed', this.trainingDetails);
    this.close.emit(this.trainingDetails);
  }

  stopPropagation(event: MouseEvent): void {
    event.stopPropagation();
  }

  // Close the popup when clicked outside of it
  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent): void {
    if (!this.isClickedInsidePopup(event)) {
        this.updateTrainingLinksLists(); // Update trainingDetails.trainingLinksLists()
        this.closePopup(); // Close the popup
    }
}

private isClickedInsidePopup(event: MouseEvent): boolean {
  const popupElement = document.querySelector('.popup-container');
  return popupElement?.contains(event.target as Node) ?? false;
}
  
private emitTrainingLinksLists(): void {
  this.trainingLinksListsChange.emit(this.trainingDetails.trainingLinksLists);
}

private saveState(): void {
  console.log("TextBoxes Values:",this.textBoxes);

  this.savedState = {
    textBoxes: this.textBoxes.map(textBox => ({
      subTrid: textBox.subTrId,
      trainingId: textBox.trainingId,
      subTrName: textBox.subTrName,
      subTrLink: textBox.subTrLink
    })),
    trainingDetails: { ...this.trainingDetails },
  };
  this.updateTrainingLinksLists();
}

private updateTrainingLinksLists(): void {
  this.trainingDetails.trainingLinksLists = this.textBoxes.map(textBox => ({
    subTrid: textBox.subTrId, // Include subTrid here
    trainingId: this.trainingDetails.id,
    subTrName: textBox.subTrName,
    subTrLink: textBox.subTrLink
  }));
}

private fetchTrainingLinks(): void {
  if (this.trainingDetails.id) {
    this.manageTrainingService.getTrainingLinks(this.trainingDetails.id).subscribe(
      (response: any) => {
        if (Array.isArray(response.data)) {
          const links: TrainingLinksModel[] = response.data;
          console.log('API Response Data:', links); // Log the API response data
          if (links.length > 0) {
            // Map the API response objects to TextBoxes array
            this.textBoxes = links.map(link => ({
              subTrId: link.subTrId || undefined, // dito problema ko
              trainingId: link.trainingId || '',
              subTrName: link.subTrName || '',
              subTrLink: link.subTrLink || '',
            }));
            console.log('TextBoxes:', this.textBoxes); // Log the TextBoxes array
            this.updateTrainingLinksLists(); // Update the training links lists
          } else {
            console.log('No training links found.');
          }
        } else {
          console.error('API response data is not an array', response.data);
        }
      },
      error => {
        console.error('Error fetching training links:', error);
      }
    );
  }
}

}
