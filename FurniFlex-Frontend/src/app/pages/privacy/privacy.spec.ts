import { TestBed } from '@angular/core/testing';
import { PrivacyPage } from './privacy';

describe('PrivacyPage', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrivacyPage]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PrivacyPage);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
