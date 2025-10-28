import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Supplier } from '../models/supplier.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SupplierService {
  private http = inject(HttpClient);

  list(): Observable<Supplier[]> {
  return this.http.get<Supplier[]>(`${environment.API_BASE}/supplier`);
  }
}
