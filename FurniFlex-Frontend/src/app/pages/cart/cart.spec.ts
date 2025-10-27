import { TestBed } from '@angular/core/testing';
import { CartPage } from './cart';
import { provideHttpClient } from '@angular/common/http';

describe('CartPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartPage],
      providers: [provideHttpClient()]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CartPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
