import { TestBed } from '@angular/core/testing';
import { AccountPage } from './account';

describe('AccountPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(AccountPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
