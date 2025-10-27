import { TestBed } from '@angular/core/testing';
import { Footer } from './footer';
import { provideRouter } from '@angular/router';

describe('Footer', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Footer],
      providers: [provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(Footer);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
