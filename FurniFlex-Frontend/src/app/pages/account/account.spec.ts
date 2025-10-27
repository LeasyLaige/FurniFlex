import { TestBed } from '@angular/core/testing';
import { AccountPage } from './account';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

describe('AccountPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountPage],
      providers: [provideHttpClient(), provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(AccountPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
