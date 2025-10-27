import { Component, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-support',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './support.html',
  styleUrl: './support.scss'
})
export class SupportPage {
  open = signal<number | null>(null);
  toggle(i: number) {
    this.open.update(curr => (curr === i ? null : i));
  }
}
