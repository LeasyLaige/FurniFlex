import { TestBed } from '@angular/core/testing';
import { SitemapPage } from './sitemap';
import { provideRouter } from '@angular/router';

describe('SitemapPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SitemapPage],
      providers: [provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(SitemapPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
