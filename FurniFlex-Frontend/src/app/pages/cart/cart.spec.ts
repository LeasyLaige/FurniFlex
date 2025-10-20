import { TestBed } from '@angular/core/testing';
import { CartPage } from './cart';

describe('CartPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CartPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
