import { Component, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductCarousel } from '../../components/product-carousel/product-carousel';
import { RevealDirective } from '../../shared/directives/reveal.directive';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';
import { InventoryService } from '../../core/services/inventory.service';

@Component({
  selector: 'app-collections',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink, ProductCarousel, RevealDirective],
  templateUrl: './collections.html',
  styleUrl: './collections.scss'
})
export class CollectionsPage {
  private productSvc = inject(ProductService);
  private cart = inject(CartService);
  private wish = inject(WishlistService);
  private route = inject(ActivatedRoute);
  private inventory = inject(InventoryService);

  products: Product[] = [];
  stock = new Map<number, { available: number }>();

  constructor() {
    this.route.queryParamMap.subscribe(params => {
      const q = params.get('q') || undefined;
      this.productSvc.list({ search: q }).subscribe(list => {
        // Attach image fallback to assets if missing
        this.products = (list || []).map((p, i) => ({
          ...p,
          image: p.image || this.fallbackImg(i)
        }));
        // Load inventory once and map availability
        this.inventory.list().subscribe(invs => {
          this.stock.clear();
          (invs || []).forEach(i => {
            const pid = i.product?.id ?? i.product; if (pid != null) this.stock.set(pid, { available: Math.max(0, (i.quantity || 0) - (i.reservedQuantity || 0)) });
          });
        });
      });
    });
  }

  addToCart(p: Product) { this.cart.add(p, 1); }
  toggleWish(p: Product) { if (p.id != null) this.wish.toggle(p.id); }
  inWish(p: Product) { return p.id != null && this.wish.has(p.id); }
  available(p: Product): number { return this.stock.get(p.id!)?.available ?? 0; }
  low(p: Product): boolean { const a = this.available(p); return a > 0 && a <= 5; }

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
