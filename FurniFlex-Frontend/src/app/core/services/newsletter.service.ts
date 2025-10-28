import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface NewsletterSubscription { id?: number; email: string; created?: string }

@Injectable({ providedIn: 'root' })
export class NewsletterService {
  private http = inject(HttpClient);
  private base = environment.API_BASE;

  subscribe(email: string): Observable<NewsletterSubscription> {
    return this.http.post<NewsletterSubscription>(`${this.base}/newsletter/subscribe`, { email });
  }
}
