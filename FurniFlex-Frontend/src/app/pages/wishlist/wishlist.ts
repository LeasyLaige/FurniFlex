import { Component, effect, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { WishlistService } from '../../core/services/wishlist.service';
import { ProductService } from '../../core/services/product.service';
import { Product } from '../../core/models/product.model';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './wishlist.html',
  styleUrl: './wishlist.scss'
})
export class WishlistPage {
  private wish = inject(WishlistService);
  private products = inject(ProductService);

  items: Product[] = [];

  constructor() {
    effect(() => {
      // Reload when wishlist ids change
      const ids = this.wish.ids();
      this.products.list().subscribe(all => {
        const byId = new Map((all || []).map(p => [p.id, p] as const));
        this.items = ids.map(id => ({ ...byId.get(id)!, image: byId.get(id)?.image || this.fallbackImg(id) })).filter(Boolean) as Product[];
      });
    });
  }

  remove(i: number) {
    const p = this.items[i];
    if (p?.id != null) this.wish.toggle(p.id);
  }

  private fallbackImg(seed: number) {
    const imgs = [
      'assets/home/new/oak-lounge.jpg',
      'assets/home/new/walnut-side.webp',
      'assets/home/new/linen-sofa.jpg',
      'assets/home/new/rattan-lamp.webp',
    ];
    return imgs[seed % imgs.length];
  }
}
