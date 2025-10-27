import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private http = inject(HttpClient);

  list(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${API_BASE}/customer`);
  }

  getById(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${API_BASE}/customer/${id}`);
  }

  create(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${API_BASE}/customer`, customer);
  }

  update(id: number, customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${API_BASE}/customer/${id}`, customer);
  }
}
