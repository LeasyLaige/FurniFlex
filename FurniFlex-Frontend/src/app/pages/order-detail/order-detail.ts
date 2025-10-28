import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { OrderService } from '../../core/services/order.service';
import { ProductService } from '../../core/services/product.service';
import { Product } from '../../core/models/product.model';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './order-detail.html',
  styleUrl: './order-detail.scss'
})
export class OrderDetailPage {
  private route = inject(ActivatedRoute);
  private orders = inject(OrderService);
  private products = inject(ProductService);

  order: any | null = null;
  items: Array<{ product: Product; quantity: number }>|null = null;
  subtotal = 0;

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.orders.getById(id).subscribe(o => {
        this.order = o;
        // Support backends that return a single top-level product + quantity
        const topLevelItem = (() => {
          const prod = (o as any)?.product;
          const pid = (Array.isArray(prod) ? (prod[0]?.id) : prod?.id)
            ?? (o as any)?.productId
            ?? (o as any)?.product_id
            ?? (Array.isArray((o as any)?.productIds) ? (o as any).productIds[0] : undefined)
            ?? (Array.isArray((o as any)?.product_id) ? (o as any).product_id[0] : undefined)
            ?? (typeof (o as any)?.product === 'number' ? (o as any).product : undefined);
          const qty = (o as any)?.quantity;
          return (pid && qty) ? [{ product: { id: pid }, quantity: qty }] : [];
        })();
        const rawItems = ((o as any)?.items && (o as any).items.length ? (o as any).items : topLevelItem) as Array<{ product?: any; quantity: number; productId?: number }>;
        // Enrich products when backend returns only IDs or null product objects
        const tasks = rawItems.map(ri => new Promise<{ product: Product; quantity: number }>((resolve) => {
          const pid = ri.product?.id
            ?? (ri.product && (ri.product.productId ?? ri.product.product_id))
            ?? (ri as any).productId
            ?? (ri as any).product_id
            ?? (typeof ri.product === 'number' ? ri.product : undefined);
          if (!pid) return resolve({ product: { id: 0, name: 'Unknown', price: 0, image: '' }, quantity: ri.quantity });
          this.products.getById(Number(pid)).subscribe(p => resolve({ product: p, quantity: ri.quantity }));
        }));
        Promise.all(tasks).then(items => {
          this.items = items;
          this.subtotal = items.reduce((s, i) => s + (i.product.price || 0) * i.quantity, 0);
        });
      });
    }
  }
}
