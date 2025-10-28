import { Component, computed, inject, signal } from '@angular/core';
import { NgIf } from '@angular/common';
import { ContactService } from '../../core/services/contact.service';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [NgIf],
  templateUrl: './contact.html',
  styleUrl: './contact.scss'
})
export class ContactPage {
  private contact = inject(ContactService);
  private acct = inject(AccountService);
  sending = signal(false);
  status = signal<'idle' | 'ok' | 'error'>('idle');
  user = computed(() => this.acct.current());

  submit(name: string, email: string, message: string) {
    const u = this.user();
    const finalName = (name && name.trim()) || u?.name || '';
    const finalEmail = (email && email.trim()) || u?.email || '';
    if (!finalName || !finalEmail || !message) return;
    this.sending.set(true);
    this.status.set('idle');
    this.contact.send({ name: finalName, email: finalEmail, message }).subscribe({
      next: () => { this.status.set('ok'); this.sending.set(false); },
      error: () => { this.status.set('error'); this.sending.set(false); }
    });
  }
}
