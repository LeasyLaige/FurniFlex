import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Inventory } from '../models/inventory.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class InventoryService {
  private http = inject(HttpClient);

  list(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${API_BASE}/inventory`);
  }

  byProductId(productId: number): Observable<Inventory | undefined> {
    return this.list().pipe(map(list => (list || []).find(i => (i.product?.id ?? i.product) === productId)));
  }

  update(inv: Inventory): Observable<Inventory> {
    if (!inv.id) throw new Error('Inventory id is required for update');
    return this.http.put<Inventory>(`${API_BASE}/inventory/${inv.id}`, inv);
  }
}
