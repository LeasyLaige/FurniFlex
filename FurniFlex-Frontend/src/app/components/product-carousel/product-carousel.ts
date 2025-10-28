import { Component, Input, ViewChild, ElementRef, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Product } from '../../core/models/product.model';
import { InventoryService } from '../../core/services/inventory.service';

@Component({
  selector: 'app-product-carousel',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink],
  templateUrl: './product-carousel.html',
  styleUrl: './product-carousel.scss'
})
export class ProductCarousel {
  @Input() title = '';
  @Input() subtitle?: string;
  @Input() cta?: { text: string; link: any[] };
  @Input() products: Product[] = [];

  @ViewChild('scroller', { static: false }) scroller?: ElementRef<HTMLElement>;

  scrollLeft() { this.scroller?.nativeElement.scrollBy({ left: -320, behavior: 'smooth' }); }
  scrollRight() { this.scroller?.nativeElement.scrollBy({ left: 320, behavior: 'smooth' }); }

  private inventory = inject(InventoryService);
  private stock = new Map<number, { available: number }>();

  ngOnInit() {
    // Load inventory once and map availability for quick badge checks
    this.inventory.list().subscribe(invs => {
      this.stock.clear();
      (invs || []).forEach(i => {
        const pid = i.product?.id ?? i.product; if (pid != null) this.stock.set(pid, { available: Math.max(0, (i.quantity || 0) - (i.reservedQuantity || 0)) });
      });
    });
  }

  available(p: Product): number { return this.stock.get(p.id!)?.available ?? 0; }
  low(p: Product): boolean { const a = this.available(p); return a > 0 && a <= 5; }
}
