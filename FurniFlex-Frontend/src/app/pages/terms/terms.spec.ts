import { TestBed } from '@angular/core/testing';
import { TermsPage } from './terms';

describe('TermsPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermsPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(TermsPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
