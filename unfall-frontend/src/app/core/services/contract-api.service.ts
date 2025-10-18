import {inject, Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {Contract} from '../models/contract.model';
import {API_BASE_URL} from '../tokens/api-base-url.token';
import {HttpClient} from '@angular/common/http';

@Injectable({ providedIn: 'root'})
export class ContractApiService {

  private readonly base: string;
  private readonly http = inject(HttpClient);
  private readonly baseUrl = inject(API_BASE_URL);

  constructor() {
    this.base = `${this.baseUrl}/contracts`;
  }

  list(): Observable<Contract[]> {
    return this.http.get<Contract[]>(this.base, {observe: 'response'})
      .pipe(map((response) => response.status === 200 ? response.body?? [] : []));
  }

  get(id: number): Observable<Contract> {
    return this.http.get<Contract>(`${this.base}/${id}`);
  }

  save(contract: Contract): Observable<Contract> {
    return this.http.post<Contract>(this.base, contract);
  }

  update(contract: Contract, id: number): Observable<Contract> {
    return this.http.put<Contract>(`${this.base}/${id}`, contract);
  }
}
