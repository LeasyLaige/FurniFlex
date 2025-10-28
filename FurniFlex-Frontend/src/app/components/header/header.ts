import { Component, computed, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';
import { SearchService } from '../../core/services/search.service';
import { AccountService } from '../../core/services/account.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgFor, NgIf, RouterLink, FormsModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  // UI state
  isNavOpen = signal(false);
  isCategoriesOpen = signal(false);
  isAccountOpen = signal(false);

  constructor(
    private cart: CartService,
    private wish: WishlistService,
    private acct: AccountService,
    private router: Router,
    private searchSvc: SearchService,
  ) {}

  // Derived counts
  cartCount = computed(() => this.cart.count());
  wishlistCount = computed(() => this.wish.count());
  user = computed(() => this.acct.current());
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

  toggleAccount() {
    this.isAccountOpen.update(v => !v);
  }

  submitSearch() {
    const q = (this.query() || '').trim();
    this.searchSvc.query.set(q);
    this.router.navigate(['/collections'], { queryParams: q ? { q } : {} });
  }

  logout() { this.acct.logout(); }

}
