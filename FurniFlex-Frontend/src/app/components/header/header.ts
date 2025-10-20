import { Component, signal } from '@angular/core';
import { NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgFor, RouterLink, FormsModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  // UI state
  isNavOpen = signal(false);
  isCategoriesOpen = signal(false);

  // Demo data; wire to real data later
  cartCount = signal(2);
  wishlistCount = signal(3);
  categories = signal([
    { name: 'Living Room', items: ['Sofas', 'Sectionals', 'Armchairs', 'Coffee Tables'] },
    { name: 'Bedroom', items: ['Beds', 'Mattresses', 'Wardrobes', 'Nightstands'] },
    { name: 'Dining', items: ['Dining Tables', 'Dining Chairs', 'Bar Stools', 'Sideboards'] },
    { name: 'Office', items: ['Desks', 'Office Chairs', 'Bookcases', 'Storage'] }
  ]);

  query = signal('');

  toggleNav() {
    this.isNavOpen.update((v) => !v);
  }

  toggleCategories() {
    this.isCategoriesOpen.update((v) => !v);
  }

  submitSearch() {
    // TODO: Navigate to search route with query when implemented
    // e.g., this.router.navigate(['/search'], { queryParams: { q: this.query() } });
    console.log('Search:', this.query());
  }

}
