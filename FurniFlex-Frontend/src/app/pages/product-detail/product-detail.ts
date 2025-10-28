import { Component, computed, inject, signal } from '@angular/core';
import { NgIf, NgFor } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { InventoryService } from '../../core/services/inventory.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';
import { ReviewService } from '../../core/services/review.service';
import { Review as ReviewModel } from '../../core/models/review.model';
import { AccountService } from '../../core/services/account.service';

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
  private reviewsApi = inject(ReviewService);
  private acct = inject(AccountService);

  readonly loading = signal(true);
  readonly product = signal<Product | null>(null);
  readonly available = signal<number>(0);

  // Live reviews fetched from backend
  readonly reviews = signal<Review[]>([]);
  // Current user
  readonly user = computed(() => this.acct.current());

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
        // Load reviews
        this.reviewsApi.byProductId(id).subscribe(list => {
          const mapped = (list || []).map(r => ({
            author: r.author,
            rating: r.rating,
            date: r.created || new Date().toISOString(),
            comment: r.comment
          }));
          this.reviews.set(mapped);
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

  submitReview(name: string, rating: number, comment: string) {
    const p = this.product();
    if (!p || !rating || !comment) return;
    const author = (name && name.trim()) || this.user()?.name || 'Anonymous';
    const payload: ReviewModel = { productId: p.id!, author, rating: Number(rating), comment };
    this.reviewsApi.create(payload).subscribe(saved => {
      const next: Review = { author: saved.author, rating: saved.rating, date: saved.created || new Date().toISOString(), comment: saved.comment };
      this.reviews.set([next, ...this.reviews()]);
    });
  }
}
