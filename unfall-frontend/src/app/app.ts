import { Component, signal } from '@angular/core';
import {RouterOutlet, Routes} from '@angular/router';
import { ContractListComponent } from './contract-list/contract-list.component';
import {ContractDetailComponent} from './contract-detail/contract-detail.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('unfall-frontend');
}

export const routes: Routes = [
  { path: '', redirectTo: '/contracts', pathMatch: 'full' },
  { path: 'contracts', component: ContractListComponent },
  { path: 'contracts/new', component: ContractDetailComponent },
  { path: 'contracts/:id', component: ContractDetailComponent }
];
