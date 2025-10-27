import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Product } from '../models/product.model';

const API_BASE = 'http://localhost:8090/api';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);

  list(params?: { search?: string; type?: string }): Observable<Product[]> {
    const url = `${API_BASE}/product`;
    return this.http.get<Product[]>(url).pipe(
      map(products => {
        let p = products || [];
        if (params?.type) {
          p = p.filter(x => (x.type || '').toLowerCase().includes(params.type!.toLowerCase()));
        }
        if (params?.search) {
          const q = params.search.toLowerCase();
          p = p.filter(x => x.name.toLowerCase().includes(q) || (x.description || '').toLowerCase().includes(q));
        }
        return p;
      })
    );
  }

  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${API_BASE}/product/${id}`);
  }

  create(product: Product): Observable<Product> {
    return this.http.post<Product>(`${API_BASE}/product`, product);
  }
}
