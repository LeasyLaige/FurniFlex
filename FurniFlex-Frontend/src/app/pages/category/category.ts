import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgFor } from '@angular/common';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [NgFor],
  templateUrl: './category.html',
  styleUrl: './category.scss'
})
export class CategoryPage {
  private route = inject(ActivatedRoute);
  private productSvc = inject(ProductService);
  private cart = inject(CartService);
  private wish = inject(WishlistService);
  get title() {
    const cat = (this.route.snapshot.paramMap.get('category') ?? 'Category').replace('-', ' ');
    const sub = this.route.snapshot.paramMap.get('sub');
    return sub ? `${cat} · ${sub}` : cat;
  }

  products: Product[] = [];

  constructor() {
    const cat = (this.route.snapshot.paramMap.get('sub') || this.route.snapshot.paramMap.get('category') || '').replace('-', ' ');
    this.productSvc.list({ type: cat }).subscribe(list => {
      this.products = (list || []).map((p, i) => ({ ...p, image: p.image || this.fallbackImg(i) }));
    });
  }

  addToCart(p: Product) { this.cart.add(p, 1); }
  toggleWish(p: Product) { if (p.id != null) this.wish.toggle(p.id); }
  inWish(p: Product) { return p.id != null && this.wish.has(p.id); }

  private fallbackImg(i: number) {
    const imgs = [
      'assets/home/rooms/living-room.jpg',
      'assets/home/rooms/bedroom.jpg',
      'assets/home/rooms/dining.webp',
      'assets/home/rooms/office.jpg',
    ];
    return imgs[i % imgs.length];
  }
}
