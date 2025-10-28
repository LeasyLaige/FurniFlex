import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private http = inject(HttpClient);

  list(): Observable<Customer[]> {
  return this.http.get<Customer[]>(`${environment.API_BASE}/customer`);
  }

  getById(id: number): Observable<Customer> {
  return this.http.get<Customer>(`${environment.API_BASE}/customer/${id}`);
  }

  create(customer: Customer): Observable<Customer> {
  return this.http.post<Customer>(`${environment.API_BASE}/customer`, customer);
  }

  update(id: number, customer: Customer): Observable<Customer> {
  return this.http.put<Customer>(`${environment.API_BASE}/customer/${id}`, customer);
  }
}
