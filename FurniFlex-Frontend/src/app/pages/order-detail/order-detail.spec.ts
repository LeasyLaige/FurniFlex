import { TestBed } from '@angular/core/testing';
import { OrderDetailPage } from './order-detail';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

describe('OrderDetailPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderDetailPage],
      providers: [provideHttpClient(), provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(OrderDetailPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
