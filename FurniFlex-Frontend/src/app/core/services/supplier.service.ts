import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Supplier } from '../models/supplier.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class SupplierService {
  private http = inject(HttpClient);

  list(): Observable<Supplier[]> {
    return this.http.get<Supplier[]>(`${API_BASE}/supplier`);
  }
}
