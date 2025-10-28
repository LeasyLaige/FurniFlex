import { TestBed } from '@angular/core/testing';
import { ProductDetailPage } from './product-detail';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { ProductService } from '../../core/services/product.service';
import { InventoryService } from '../../core/services/inventory.service';
import { CartService } from '../../core/services/cart.service';
import { WishlistService } from '../../core/services/wishlist.service';

class MockProductService {
  getById = jasmine.createSpy('getById').and.returnValue(of({
    id: 42,
    name: 'Walnut Lounge Sofa',
    description: 'A timeless silhouette in a durable linen blend.',
    type: 'Sofas',
    price: 1299,
    image: '/assets/sample.jpg',
    sku: 'FF-SF-0042',
    dimensions: '84” W x 35” D x 30” H',
    material: 'Solid walnut, Linen blend',
    color: 'Walnut / Charcoal',
    weight: '95 lb',
  }))
}

class MockInventoryService {
  byProductId = jasmine.createSpy('byProductId').and.returnValue(of({ quantity: 10, reservedQuantity: 2 }))
}

class MockCartService { add = jasmine.createSpy('add'); }
class MockWishlistService { has = () => false; toggle = jasmine.createSpy('toggle'); }

describe('ProductDetailPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductDetailPage],
      providers: [
        { provide: ActivatedRoute, useValue: { paramMap: of(new Map([['id', '42']])) } },
        { provide: ProductService, useClass: MockProductService },
        { provide: InventoryService, useClass: MockInventoryService },
        { provide: CartService, useClass: MockCartService },
        { provide: WishlistService, useClass: MockWishlistService },
      ]
    }).compileComponents();
  });

  it('renders product basics and specs', () => {
    const fixture = TestBed.createComponent(ProductDetailPage);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.textContent).toContain('Walnut Lounge Sofa');
    expect(el.textContent).toContain('SKU');
    expect(el.textContent).toContain('FF-SF-0042');
    expect(el.textContent).toContain('Dimensions');
    expect(el.textContent).toContain('84” W x 35” D x 30” H');
  });

  it('adds to cart when clicked', () => {
    const fixture = TestBed.createComponent(ProductDetailPage);
  const cart = TestBed.inject(CartService) as unknown as MockCartService;
    fixture.detectChanges();
    const btn = (fixture.nativeElement as HTMLElement).querySelector('button.btn-primary') as HTMLButtonElement;
    btn.click();
    expect(cart.add).toHaveBeenCalled();
  });
});
