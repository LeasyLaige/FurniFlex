import { Component, computed, inject } from '@angular/core';
import { NgFor } from '@angular/common';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';
import { AccountService } from '../../core/services/account.service';
import { Router } from '@angular/router';
import { InventoryService } from '../../core/services/inventory.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NgFor],
  templateUrl: './cart.html',
  styleUrl: './cart.scss'
})
export class CartPage {
  private cart = inject(CartService);
  private orders = inject(OrderService);
  private acct = inject(AccountService);
  private router = inject(Router);
  private inventory = inject(InventoryService);

  items = this.cart.items;
  subtotal = computed(() => this.cart.subtotal());

  checkout() {
    const user = this.acct.current();
    if (!user) {
      alert('Please sign in or create an account before checkout.');
      return;
    }
    const list = this.items();
    if (!list.length) return;

    // Validate stock first
    this.inventory.list().subscribe(invs => {
      const byProduct = new Map<number, any>();
      (invs || []).forEach(i => { const pid = i.product?.id ?? i.product; if (pid != null) byProduct.set(pid, i); });

      const problems: string[] = [];
      for (const ci of list) {
        const inv = byProduct.get(ci.product.id!);
        const available = Math.max(0, (inv?.quantity || 0) - (inv?.reservedQuantity || 0));
        if (!inv || available <= 0) {
          problems.push(`${ci.product.name} is out of stock.`);
        } else if (ci.quantity > available) {
          problems.push(`${ci.product.name}: only ${available} left (you have ${ci.quantity} in cart).`);
        }
      }

      if (problems.length) {
        alert('Stock issue:\n' + problems.join('\n'));
        return;
      }

      // Create a single order with multiple items
      const payload = {
        customer: { id: user.id },
        items: list.map(ci => ({ product: { id: ci.product.id }, quantity: ci.quantity })),
        status: 'Ordered' as const
      };

      this.orders.create(payload as any).subscribe({
        next: () => {
          // Deduct stock for each item (best-effort, sequential)
          const updates = list.map(ci => new Promise<void>(resolve => {
            const inv = byProduct.get(ci.product.id!);
            if (inv?.id) {
              const newQty = Math.max(0, (inv.quantity || 0) - ci.quantity);
              this.inventory.update({ ...inv, quantity: newQty }).subscribe({ next: () => resolve(), error: () => resolve() });
            } else {
              resolve();
            }
          }));
          Promise.all(updates).then(() => {
            this.cart.clear();
            this.router.navigate(['/account']);
          });
        },
        error: err => {
          alert('Could not create order. Please try again.');
        }
      });
    });
  }
}
