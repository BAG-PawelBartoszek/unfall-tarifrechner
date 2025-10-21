export interface Contract {
  id: number;
  contractNumber: string;
  persons: Person[];
  totalMonthlyPremium: number;
}

export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  coverages: Coverage[];
  monthlyPremium: number;
}

export interface Coverage {
  id: number;
  type: CoverageType;
  premiumMonthly: number;
  insuredSum: number;
}

export type CoverageType = 'INVALIDITAET' | 'GIPSGELD' | 'TOD';

export function coverageLabel(coverageType: CoverageType) {
    switch (coverageType) {
        case 'INVALIDITAET':
            return 'Invalidität';
        case 'GIPSGELD':
            return 'Gipsgeld';
        case 'TOD':
            return 'Tod';
    }
}
