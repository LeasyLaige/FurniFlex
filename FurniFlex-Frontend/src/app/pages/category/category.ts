import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [NgFor],
  templateUrl: './category.html',
  styleUrl: './category.scss'
})
export class CategoryPage {
  private route = inject(ActivatedRoute);
  get title() {
    const cat = (this.route.snapshot.paramMap.get('category') ?? 'Category').replace('-', ' ');
    const sub = this.route.snapshot.paramMap.get('sub');
    return sub ? `${cat} · ${sub}` : cat;
  }

  products = [
    { name: 'Cozy Sofa', price: 799, img: 'assets/home/rooms/living-room.jpg' },
    { name: 'Bed Frame', price: 649, img: 'assets/home/rooms/bedroom.jpg' },
    { name: 'Dining Set', price: 999, img: 'assets/home/rooms/dining.webp' },
    { name: 'Desk Chair', price: 199, img: 'assets/home/rooms/office.jpg' },
  ];
}
