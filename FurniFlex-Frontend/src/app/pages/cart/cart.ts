import { Component, computed, inject } from '@angular/core';
import { NgFor } from '@angular/common';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';
import { AccountService } from '../../core/services/account.service';

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

    // Create one order per cart item
    let remaining = list.length;
    list.forEach(ci => {
      this.orders.create({ customer: { id: user.id }, product: { id: ci.product.id }, quantity: ci.quantity, status: 'Ordered' }).subscribe({
        next: () => { remaining--; if (remaining === 0) { this.cart.clear(); alert('Order placed! You can view receipts in your Account → Orders.'); } },
        error: () => { remaining--; if (remaining === 0) { alert('Some items failed to order. Check Account → Orders.'); } }
      });
    });
  }
}
