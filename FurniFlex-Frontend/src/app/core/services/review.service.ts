import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Review } from '../models/review.model';

@Injectable({ providedIn: 'root' })
export class ReviewService {
  private http = inject(HttpClient);
  private base = environment.API_BASE;

  byProductId(productId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.base}/reviews/product/${productId}`);
  }

  create(payload: Review): Observable<Review> {
    return this.http.post<Review>(`${this.base}/reviews`, payload);
  }
}
