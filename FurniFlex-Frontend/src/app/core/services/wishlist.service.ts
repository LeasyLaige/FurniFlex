import { Injectable, signal } from '@angular/core';

const STORAGE_KEY = 'ff_wishlist';

@Injectable({ providedIn: 'root' })
export class WishlistService {
  readonly ids = signal<number[]>(this.load());

  private load(): number[] {
    try { return JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]'); } catch { return []; }
  }
  private save() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.ids()))
  }

  toggle(productId: number) {
    const set = new Set(this.ids());
    if (set.has(productId)) set.delete(productId); else set.add(productId);
    this.ids.set(Array.from(set));
    this.save();
  }

  has(productId: number): boolean { return this.ids().includes(productId); }
  count(): number { return this.ids().length; }
  clear() { this.ids.set([]); this.save(); }
}
