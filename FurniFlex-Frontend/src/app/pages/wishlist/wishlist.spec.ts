import { TestBed } from '@angular/core/testing';
import { WishlistPage } from './wishlist';

describe('WishlistPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WishlistPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(WishlistPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
