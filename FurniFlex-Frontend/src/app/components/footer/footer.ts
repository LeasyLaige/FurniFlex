import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { NewsletterService } from '../../core/services/newsletter.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class Footer {
  currentYear = new Date().getFullYear();

  private newsletter = inject(NewsletterService);
  submitting = signal(false);
  status = signal<'idle' | 'ok' | 'error'>('idle');

  onSubscribe(email: string) {
    if (!email) return;
    this.submitting.set(true);
    this.status.set('idle');
    this.newsletter.subscribe(email).subscribe({
      next: () => { this.status.set('ok'); this.submitting.set(false); },
      error: () => { this.status.set('error'); this.submitting.set(false); }
    });
  }
}
