import { Component } from '@angular/core';
import { NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-collections',
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: './collections.html',
  styleUrl: './collections.scss'
})
export class CollectionsPage {
  products = [
    { name: 'Oak Lounge Chair', price: 289, img: 'assets/home/new/oak-lounge.jpg' },
    { name: 'Walnut Side Table', price: 149, img: 'assets/home/new/walnut-side.webp' },
    { name: 'Linen Sofa 3-Seater', price: 899, img: 'assets/home/new/linen-sofa.jpg' },
    { name: 'Rattan Accent Lamp', price: 79, img: 'assets/home/new/rattan-lamp.webp' },
  ];
}
