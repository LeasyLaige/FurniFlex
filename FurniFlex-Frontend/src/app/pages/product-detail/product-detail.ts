import { Component, computed, inject, signal } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { InventoryService } from '../../core/services/inventory.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';

interface Review { author: string; rating: number; date: string; comment: string; }

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [NgIf, NgFor, RouterLink, DatePipe],
  templateUrl: './product-detail.html',
  styleUrl: './product-detail.scss'
})
export class ProductDetailPage {
  private route = inject(ActivatedRoute);
  private products = inject(ProductService);
  private inventory = inject(InventoryService);
  private cart = inject(CartService);
  private wish = inject(WishlistService);

  readonly loading = signal(true);
  readonly product = signal<Product | null>(null);
  readonly available = signal<number>(0);

  // Demo reviews (placeholder until backend provides these)
  readonly reviews = signal<Review[]>([
    { author: 'Alex M.', rating: 5, date: '2025-09-12', comment: 'Excellent quality and finish. Worth every penny.' },
    { author: 'Jamie L.', rating: 4, date: '2025-10-01', comment: 'Looks great, slightly firmer than expected.' },
    { author: 'Taylor R.', rating: 5, date: '2025-10-18', comment: 'Fast delivery and the packaging was solid. Love it!' }
  ]);

  readonly avgRating = computed(() => {
    const list = this.reviews();
    if (!list.length) return 0;
    return Math.round((list.reduce((s, r) => s + r.rating, 0) / list.length) * 10) / 10;
  });

  constructor() {
    this.route.paramMap.subscribe(params => {
      const raw = params.get('id');
      const id = raw ? Number(raw) : NaN;
      if (!id) return;
      this.loading.set(true);
      this.products.getById(id).subscribe(p => {
        const withImage = { ...p, image: p.image || this.fallbackImg(p) };
        this.product.set(withImage);
        this.loading.set(false);
        // Load inventory for availability
        this.inventory.byProductId(id).subscribe(inv => {
          const available = Math.max(0, (inv?.quantity || 0) - (inv?.reservedQuantity || 0));
          this.available.set(available);
        });
      });
    });
  }

  addToCart() {
    const p = this.product();
    if (!p) return;
    this.cart.add(p, 1);
  }

  toggleWish() {
    const p = this.product();
    if (!p?.id) return;
    this.wish.toggle(p.id);
  }

  inWish(): boolean {
    const p = this.product();
    return !!p?.id && this.wish.has(p.id);
  }

  private fallbackImg(p: Product) {
    return 'assets/home/new/oak-lounge.jpg';
  }
}
