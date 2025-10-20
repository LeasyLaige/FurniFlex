import { TestBed } from '@angular/core/testing';
import { ContactPage } from './contact';

describe('ContactPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(ContactPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
