import { TestBed } from '@angular/core/testing';
import { WishlistPage } from './wishlist';
import { provideHttpClient } from '@angular/common/http';

describe('WishlistPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WishlistPage],
      providers: [provideHttpClient()]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(WishlistPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
