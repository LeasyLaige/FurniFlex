import { Component, inject } from '@angular/core';
import { NgFor } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';

@Component({
  selector: 'app-collections',
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: './collections.html',
  styleUrl: './collections.scss'
})
export class CollectionsPage {
  private productSvc = inject(ProductService);
  private cart = inject(CartService);
  private wish = inject(WishlistService);
  private route = inject(ActivatedRoute);

  products: Product[] = [];

  constructor() {
    this.route.queryParamMap.subscribe(params => {
      const q = params.get('q') || undefined;
      this.productSvc.list({ search: q }).subscribe(list => {
        // Attach image fallback to assets if missing
        this.products = (list || []).map((p, i) => ({
          ...p,
          image: p.image || this.fallbackImg(i)
        }));
      });
    });
  }

  addToCart(p: Product) { this.cart.add(p, 1); }
  toggleWish(p: Product) { if (p.id != null) this.wish.toggle(p.id); }
  inWish(p: Product) { return p.id != null && this.wish.has(p.id); }

  private fallbackImg(i: number) {
    const imgs = [
      'assets/home/new/oak-lounge.jpg',
      'assets/home/new/walnut-side.webp',
      'assets/home/new/linen-sofa.jpg',
      'assets/home/new/rattan-lamp.webp',
    ];
    return imgs[i % imgs.length];
  }
}
