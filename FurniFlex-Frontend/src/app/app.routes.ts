import { Routes } from '@angular/router';
import { Home } from './components/home/home';

export const routes: Routes = [
		{ path: '', component: Home, pathMatch: 'full' },
		{ path: 'collections', loadComponent: () => import('./pages/collections/collections').then(m => m.CollectionsPage) },
		{ path: 'about', loadComponent: () => import('./pages/about/about').then(m => m.AboutPage) },
		{ path: 'contact', loadComponent: () => import('./pages/contact/contact').then(m => m.ContactPage) },
		{ path: 'wishlist', loadComponent: () => import('./pages/wishlist/wishlist').then(m => m.WishlistPage) },
		{ path: 'account', loadComponent: () => import('./pages/account/account').then(m => m.AccountPage) },
		{ path: 'cart', loadComponent: () => import('./pages/cart/cart').then(m => m.CartPage) },
		{ path: 'category/:category', loadComponent: () => import('./pages/category/category').then(m => m.CategoryPage) },
		{ path: 'category/:category/:sub', loadComponent: () => import('./pages/category/category').then(m => m.CategoryPage) },
		{ path: 'careers', loadComponent: () => import('./pages/careers/careers').then(m => m.CareersPage) },
		{ path: 'support', loadComponent: () => import('./pages/support/support').then(m => m.SupportPage) },
		{ path: 'privacy', loadComponent: () => import('./pages/privacy/privacy').then(m => m.PrivacyPage) },
		{ path: 'terms', loadComponent: () => import('./pages/terms/terms').then(m => m.TermsPage) },
		{ path: 'sitemap', loadComponent: () => import('./pages/sitemap/sitemap').then(m => m.SitemapPage) },
    { path: 'order/:id', loadComponent: () => import('./pages/order-detail/order-detail').then(m => m.OrderDetailPage) },
];
