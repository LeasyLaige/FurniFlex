import { TestBed } from '@angular/core/testing';
import { CategoryPage } from './category';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

describe('CategoryPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryPage],
      providers: [provideHttpClient(), provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CategoryPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
