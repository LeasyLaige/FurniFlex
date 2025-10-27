import { Component } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './wishlist.html',
  styleUrl: './wishlist.scss'
})
export class WishlistPage {
  items = [
    { name: 'Oak Lounge Chair', price: 289, img: 'assets/home/new/oak-lounge.jpg' },
    { name: 'Walnut Side Table', price: 149, img: 'assets/home/new/walnut-side.webp' }
  ];

  remove(i: number) { this.items.splice(i, 1); }
}
