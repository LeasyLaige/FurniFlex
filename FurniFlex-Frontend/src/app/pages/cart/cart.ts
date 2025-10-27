import { Component } from '@angular/core';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NgFor],
  templateUrl: './cart.html',
  styleUrl: './cart.scss'
})
export class CartPage {
  items = [
    { name: 'Oak Lounge Chair', price: 289, qty: 1 },
    { name: 'Walnut Side Table', price: 149, qty: 2 }
  ];

  get subtotal() { return this.items.reduce((s, i) => s + i.price * i.qty, 0); }
}
