import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ContractApiService } from '../core/services/contract-api.service';
import { Contract, coverageLabel, CoverageType } from '../core/models/contract.model';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {DecimalPipe} from '@angular/common';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-contract-list',
  templateUrl: './contract-list.component.html',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardContent,
    DecimalPipe,
    MatButton
  ],
  styleUrls: ['./contract-list.component.css']
})
export class ContractListComponent implements OnInit {
  private contractService = inject(ContractApiService);
  private router = inject(Router);

  contracts: Contract[] = [];
  loading = false;
  coverageLabel = coverageLabel;

  ngOnInit(): void {
    this.loadContracts();
  }

  loadContracts(): void {
    this.loading = true;
    this.contractService.list().subscribe({
      next: (contracts) => {
        this.contracts = contracts;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  openContract(contract: Contract): void {
    if (contract.id) {
      this.router.navigate(['/contracts', contract.id]);
    }
  }

  createNew(): void {
    this.router.navigate(['/contracts', 'new']);
  }

}
