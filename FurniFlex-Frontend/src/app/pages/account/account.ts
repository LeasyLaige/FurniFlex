import { Component, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [FormsModule, NgIf, NgFor, RouterLink],
  templateUrl: './account.html',
  styleUrl: './account.scss'
})
export class AccountPage {
  private acct = inject(AccountService);
  user = computed(() => this.acct.current());
  orders = signal<any[]>([]);

  // Forms
  loginEmail = signal('');
  loginPassword = signal('');
  regFirst = signal('');
  regLast = signal('');
  regEmail = signal('');
  regPhone = signal('');
  regPassword = signal('');

  signIn() {
    const email = this.loginEmail().trim();
    const pwd = this.loginPassword().trim();
    if (!email || !pwd) return;
    this.acct.login(email, pwd).subscribe(found => {
      if (!found) alert('Invalid email or password.');
      else this.loadOrders();
    });
  }

  createAccount() {
    const name = `${this.regFirst().trim()} ${this.regLast().trim()}`.trim();
    const email = this.regEmail().trim();
    const phone = this.regPhone().trim() || undefined;
    const pwd = this.regPassword().trim();
    if (!name || !email || !pwd) return;
    this.acct.register(name, email, phone, pwd).subscribe(() => {
      this.loadOrders();
    });
  }

  logout() { this.acct.logout(); this.orders.set([]); }

  loadOrders() {
    this.acct.myOrders().subscribe(list => this.orders.set(list));
  }
}
