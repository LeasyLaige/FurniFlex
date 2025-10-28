import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { Product } from '../../core/models/product.model';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';
import { InventoryService } from '../../core/services/inventory.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink],
  templateUrl: './category.html',
  styleUrl: './category.scss'
})
export class CategoryPage {
  private route = inject(ActivatedRoute);
  private productSvc = inject(ProductService);
  private cart = inject(CartService);
  private wish = inject(WishlistService);
  private inventory = inject(InventoryService);

  private categoryMap: Record<string, string[]> = {
    'Living Room': ['Sofas', 'Sectionals', 'Armchairs', 'Coffee Tables'],
    'Bedroom': ['Beds', 'Mattresses', 'Wardrobes', 'Nightstands'],
    'Dining': ['Dining Tables', 'Dining Chairs', 'Bar Stools', 'Sideboards'],
    'Office': ['Desks', 'Office Chairs', 'Bookcases', 'Storage']
  };
  get title() {
    const cat = (this.route.snapshot.paramMap.get('category') ?? 'Category').replace('-', ' ');
    const sub = this.route.snapshot.paramMap.get('sub');
    return sub ? `${cat} · ${sub}` : cat;
  }

  products: Product[] = [];
  stock = new Map<number, { available: number }>();

  constructor() {
    this.route.paramMap.subscribe(pm => {
      const category = (pm.get('category') || '').replace('-', ' ');
      const sub = (pm.get('sub') || '').replace('-', ' ');

      // If a sub-type is provided, filter by that type. Otherwise aggregate by top-level category.
      if (sub) {
        this.productSvc.list({ type: sub }).subscribe(list => {
          this.products = (list || []).map((p, i) => ({ ...p, image: p.image || this.fallbackImg(i) }));
          this.loadStock();
        });
      } else if (category && this.categoryMap[category]) {
        this.productSvc.list().subscribe(list => {
          const types = new Set(this.categoryMap[category]);
          const filtered = (list || []).filter(p => p.type && types.has(p.type));
          this.products = filtered.map((p, i) => ({ ...p, image: p.image || this.fallbackImg(i) }));
          this.loadStock();
        });
      } else {
        // Fallback: list all
        this.productSvc.list().subscribe(list => {
          this.products = (list || []).map((p, i) => ({ ...p, image: p.image || this.fallbackImg(i) }));
          this.loadStock();
        });
      }
    });
  }

  addToCart(p: Product) { this.cart.add(p, 1); }
  toggleWish(p: Product) { if (p.id != null) this.wish.toggle(p.id); }
  inWish(p: Product) { return p.id != null && this.wish.has(p.id); }
  available(p: Product): number { return this.stock.get(p.id!)?.available ?? 0; }
  low(p: Product): boolean { const a = this.available(p); return a > 0 && a <= 5; }

  private loadStock() {
    this.inventory.list().subscribe(invs => {
      this.stock.clear();
      (invs || []).forEach(i => { const pid = i.product?.id ?? i.product; if (pid != null) this.stock.set(pid, { available: Math.max(0, (i.quantity || 0) - (i.reservedQuantity || 0)) }); });
    });
  }

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
