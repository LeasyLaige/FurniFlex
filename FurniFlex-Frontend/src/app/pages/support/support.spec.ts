import { TestBed } from '@angular/core/testing';
import { SupportPage } from './support';

describe('SupportPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SupportPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(SupportPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
