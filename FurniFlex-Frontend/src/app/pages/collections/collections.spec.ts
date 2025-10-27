import { TestBed } from '@angular/core/testing';
import { CollectionsPage } from './collections';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

describe('CollectionsPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionsPage],
      providers: [provideHttpClient(), provideRouter([])]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CollectionsPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
