import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { concatMap, exhaustMap, filter, map, mergeMap, noop, of, switchMap, tap } from 'rxjs';
import { SearchService } from './app.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  public searchForm = this._fb.group({
    searchTerm: [''],
  })

  constructor(
    private _fb: FormBuilder,
    private _searchService: SearchService
  ) {}

  ngOnInit(): void {
    this.searchForm.get('searchTerm')?.valueChanges.pipe(
      filter((searchTerm: any) => !!searchTerm),
      mergeMap((searchTerm: string) => this._searchService.search(searchTerm)),
    ).subscribe(noop)
  }
  
}
