import { TestBed } from '@angular/core/testing';
import { CategoryPage } from './category';

describe('CategoryPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CategoryPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
