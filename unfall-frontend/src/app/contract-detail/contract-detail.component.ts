import {Component, inject, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatSelect, MatOption} from '@angular/material/select';
import {ActivatedRoute, Router} from '@angular/router';
import {ContractApiService} from '../core/services/contract-api.service';
import {Contract, CoverageType, coverageLabel} from '../core/models/contract.model';

@Component({
  selector: 'app-contract-detail',
  imports: [ReactiveFormsModule,
    MatButton,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardContent,
    MatFormField,
    MatInput,
    MatLabel,
    MatSelect,
    MatOption
  ],
  templateUrl: './contract-detail.component.html',
  styleUrl: './contract-detail.component.css'
})
export class ContractDetailComponent implements OnInit{
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private contractService = inject(ContractApiService);

  form: FormGroup;
  isNew = true;
  coverageTypes: CoverageType[] = ['INVALIDITAET', 'GIPSGELD', 'TOD'];
  coverageLabel = coverageLabel;

  constructor() {
    this.form = this.fb.group({
      id: [null],
      contractNumber: ['', Validators.required],
      persons: this.fb.array([]),
      totalMonthlyPremium: [0]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'new') {
      this.isNew = true;
      this.form.patchValue({
        contractNumber: this.generateContractNumber()
      });
    } else if (id) {
      this.isNew = false;
      this.loadContract(+id);
    }
  }

  get persons(): FormArray {
    return this.form.get('persons') as FormArray;
  }

  getCoverages(personIndex: number): FormArray {
    return this.persons.at(personIndex).get('coverages') as FormArray;
  }

  addPerson(): void {
    const personGroup = this.fb.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      coverages: this.fb.array([]),
      monthlyPremium: [0]
    });
    this.persons.push(personGroup);
  }

  removePerson(index: number): void {
    this.persons.removeAt(index);
  }

  addCoverage(personIndex: number): void {
    const coverageGroup = this.fb.group({
      id: [null],
      type: ['INVALIDITAET', Validators.required]
    });
    this.getCoverages(personIndex).push(coverageGroup);
  }

  removeCoverage(personIndex: number, coverageIndex: number): void {
    this.getCoverages(personIndex).removeAt(coverageIndex);
  }

  loadContract(id: number): void {
    this.contractService.get(id).subscribe({
      next: (contract) => {
        this.form.patchValue({
          id: contract.id,
          contractNumber: contract.contractNumber,
          totalMonthlyPremium: contract.totalMonthlyPremium
        });
        contract.persons.forEach((person) => {
          const personGroup = this.fb.group({
            id: [person.id],
            firstName: [person.firstName, Validators.required],
            lastName: [person.lastName, Validators.required],
            coverages: this.fb.array([]),
            monthlyPremium: [person.monthlyPremium]
          });
          person.coverages.forEach((coverage) => {
            const coverageGroup = this.fb.group({
              id: [coverage.id],
              type: [coverage.type, Validators.required]
            });
            (personGroup.get('coverages') as FormArray).push(coverageGroup);
          });
          this.persons.push(personGroup);
        });
      },
      error: () => {
        alert('Vertrag konnte nicht geladen werden');
        this.router.navigate(['/contracts']);
      }
    });
  }

  save(): void {
    if (this.form.invalid) {
      alert('Bitte füllen Sie alle Pflichtfelder aus');
      return;
    }
    const contract: Contract = this.form.value;
    this.contractService.save(contract).subscribe({
      next: (saved) => {
        this.router.navigate(['/contracts']);
      },
      error: () => {
        alert('Fehler beim Speichern des Vertrags');
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/contracts']);
  }

  private generateContractNumber(): string {
    return 'UV-' + Math.floor(Math.random() * 100000).toString().padStart(6, '0');
  }
}
