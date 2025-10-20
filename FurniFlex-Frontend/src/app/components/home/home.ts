import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, NgFor],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit, OnDestroy {
  rooms = [
    { name: 'Living Room', img: 'assets/home/rooms/living-room.jpg' },
    { name: 'Bedroom', img: 'assets/home/rooms/bedroom.jpg' },
    { name: 'Dining', img: 'assets/home/rooms/dining.webp' },
    { name: 'Office', img: 'assets/home/rooms/office.jpg' }
  ];

  onImgError(ev: Event) {
    const el = ev.target as HTMLImageElement;
    // Optionally swap to a subtle placeholder color block
    el.src = 'data:image/svg+xml;utf8,' + encodeURIComponent(`<svg xmlns="http://www.w3.org/2000/svg" width="400" height="400">
      <rect width="100%" height="100%" fill="#EAE0D5"/>
      <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#6B5B53" font-family="Arial" font-size="18">Image unavailable</text>
    </svg>`);
  }

  // Hero slider state
  heroImages = [
    { src: 'assets/home/inspo/minimal-living.jpg', alt: 'Minimal living space' },
    { src: 'assets/home/inspo/natural-dining.webp', alt: 'Natural dining setup' },
    { src: 'assets/home/rooms/living-room.jpg', alt: 'Living room furniture' },
    { src: 'assets/home/rooms/bedroom.jpg', alt: 'Bedroom furniture' }
  ];
  heroIndex = 0;
  private heroTimer?: any;
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {
    // Autoplay every 6s (browser only)
    if (isPlatformBrowser(this.platformId)) {
      this.heroTimer = setInterval(() => this.nextHero(), 6000);
    }
  }

  ngOnDestroy(): void {
    if (this.heroTimer) clearInterval(this.heroTimer);
  }

  goHero(i: number) { this.heroIndex = (i + this.heroImages.length) % this.heroImages.length; }
  nextHero() { this.goHero(this.heroIndex + 1); }
  prevHero() { this.goHero(this.heroIndex - 1); }
}
