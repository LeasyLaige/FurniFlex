import { TestBed } from '@angular/core/testing';
import { AboutPage } from './about';

describe('AboutPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(AboutPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
