import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface ContactMessage { id?: number; name: string; email: string; subject?: string; message: string; created?: string }

@Injectable({ providedIn: 'root' })
export class ContactService {
  private http = inject(HttpClient);
  private base = environment.API_BASE;

  send(payload: ContactMessage): Observable<ContactMessage> {
    return this.http.post<ContactMessage>(`${this.base}/contact`, payload);
  }
}
