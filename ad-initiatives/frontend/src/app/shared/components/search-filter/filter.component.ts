import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';

@Component({
  selector: 'search-filter',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent implements OnInit{
  searchQuery: string = '';
  items: string[] = [
    'Item 1',
    'Item 2',
    'Item 3',
    'Another Item 1',
    'Another Item 2',
    'Different Item 1',
    'Different Item 2'
  ];
  searchResults: string[] = [];
  selectedItems: string[] = [];
  showBoxContainer: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.filterItems('');
  }

  filterItems(event: any): void {
    this.searchQuery = event.target.value;
    if (this.searchQuery.trim() !== '') {
      this.searchResults = this.items.filter(item =>
        !this.selectedItems.includes(item) &&
        item.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.searchResults = [];
    }
  }

  addToBox(item: string): void {
    this.selectedItems.push(item);
    this.removeItemFromSearchResults(item);
  }

  removeFromBox(item: string): void {
    const index = this.selectedItems.findIndex(i => i === item);
    if (index !== -1) {
      this.selectedItems.splice(index, 1);
      this.filterItems({ target: { value: this.searchQuery } }); // Update search results
    }
  }

  private removeItemFromSearchResults(item: string): void {
    this.searchResults = this.searchResults.filter(result => result !== item);
  }

  toggleBoxContainer(): void {
    this.showBoxContainer = !this.showBoxContainer;
  }

  onDone(): void {
    this.searchQuery = this.selectedItems.join(', ');
    this.showBoxContainer = false;
  }

  get displayedSearchQuery(): string {
    return this.searchQuery;
  }
}