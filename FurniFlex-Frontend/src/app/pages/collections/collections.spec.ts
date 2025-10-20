import { TestBed } from '@angular/core/testing';
import { CollectionsPage } from './collections';

describe('CollectionsPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionsPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CollectionsPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
