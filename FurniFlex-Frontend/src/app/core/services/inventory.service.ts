import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventory } from '../models/inventory.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class InventoryService {
  private http = inject(HttpClient);

  list(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${API_BASE}/inventory`);
  }
}
