import { Directive, ElementRef, Inject, PLATFORM_ID, Renderer2, OnInit, OnDestroy } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Directive({
  selector: '[appReveal]',
  standalone: true
})
export class RevealDirective implements OnInit, OnDestroy {
  private observer?: IntersectionObserver;

  constructor(
    private el: ElementRef<HTMLElement>,
    private renderer: Renderer2,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      // Rendered on server: immediately mark visible to avoid layout shift
      this.renderer.addClass(this.el.nativeElement, 'reveal-visible');
      return;
    }

    this.renderer.addClass(this.el.nativeElement, 'reveal');

    this.observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          this.renderer.addClass(this.el.nativeElement, 'reveal-visible');
          this.observer?.unobserve(this.el.nativeElement);
        }
      });
    }, { threshold: 0.15 });

    this.observer.observe(this.el.nativeElement);
  }

  ngOnDestroy(): void { this.observer?.disconnect(); }
}
