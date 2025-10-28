import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateOrderPayload, Order } from '../models/order.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private http = inject(HttpClient);

  list(): Observable<Order[]> {
  return this.http.get<Order[]>(`${environment.API_BASE}/order`);
  }

  getById(id: number): Observable<Order> {
  return this.http.get<Order>(`${environment.API_BASE}/order/${id}`);
  }

  create(payload: CreateOrderPayload): Observable<Order> {
  return this.http.post<Order>(`${environment.API_BASE}/order`, payload);
  }
}
