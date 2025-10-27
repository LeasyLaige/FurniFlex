import { inject, Injectable, signal } from '@angular/core';
import { Customer } from '../models/customer.model';
import { CustomerService } from './customer.service';
import { OrderService } from './order.service';
import { Observable, map } from 'rxjs';

const STORAGE_KEY = 'ff_user';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private customers = inject(CustomerService);
  private orders = inject(OrderService);

  readonly current = signal<Customer | null>(this.load());

  private load(): Customer | null {
    try { return JSON.parse(localStorage.getItem(STORAGE_KEY) || 'null'); } catch { return null; }
  }
  private save() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.current()));
  }

  register(name: string, email: string, phone?: string, password?: string) {
    return this.customers.create({ name, email, phone, password }).pipe(map(user => {
      this.current.set(user);
      this.save();
      return user;
    }));
  }

  login(email: string, password: string): Observable<Customer | null> {
    return this.customers.list().pipe(map(list => {
      const user = (list || []).find(u => u.email?.toLowerCase() === email.toLowerCase() && (u as any).password === password) || null;
      if (user) { this.current.set(user); this.save(); }
      return user;
    }));
  }

  logout() { this.current.set(null); this.save(); }

  myOrders(): Observable<any[]> {
    return this.orders.list().pipe(map(all => {
      const me = this.current();
      if (!me) return [];
      // backend order.customer is CustomerData; try id match
      return (all || []).filter(o => (o.customer?.id ?? o.customer?.customerId) === me.id);
    }));
  }
}
