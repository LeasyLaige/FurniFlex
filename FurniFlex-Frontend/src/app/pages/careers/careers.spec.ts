import { TestBed } from '@angular/core/testing';
import { CareersPage } from './careers';

describe('CareersPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CareersPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CareersPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
