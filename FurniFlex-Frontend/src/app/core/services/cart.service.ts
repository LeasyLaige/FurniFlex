import { Injectable, signal } from '@angular/core';
import { CartItem } from '../models/cart.model';
import { Product } from '../models/product.model';

const STORAGE_KEY = 'ff_cart';

@Injectable({ providedIn: 'root' })
export class CartService {
  readonly items = signal<CartItem[]>(this.load());

  private load(): CartItem[] {
    try { return JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]'); } catch { return []; }
  }
  private save() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.items()))
  }

  add(product: Product, quantity = 1) {
    const list = [...this.items()];
    const idx = list.findIndex(i => i.product.id === product.id);
    if (idx >= 0) list[idx] = { ...list[idx], quantity: list[idx].quantity + quantity };
    else list.push({ product, quantity });
    this.items.set(list);
    this.save();
  }

  remove(productId: number) {
    this.items.set(this.items().filter(i => i.product.id !== productId));
    this.save();
  }

  clear() {
    this.items.set([]);
    this.save();
  }

  count(): number { return this.items().reduce((n, i) => n + i.quantity, 0); }
  subtotal(): number { return this.items().reduce((s, i) => s + i.quantity * (i.product.price || 0), 0); }
}
