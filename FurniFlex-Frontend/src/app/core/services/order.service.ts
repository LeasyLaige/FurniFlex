import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateOrderPayload, Order } from '../models/order.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private http = inject(HttpClient);

  list(): Observable<Order[]> {
    return this.http.get<Order[]>(`${API_BASE}/order`);
  }

  getById(id: number): Observable<Order> {
    return this.http.get<Order>(`${API_BASE}/order/${id}`);
  }

  create(payload: CreateOrderPayload): Observable<Order> {
    return this.http.post<Order>(`${API_BASE}/order`, payload);
  }
}
