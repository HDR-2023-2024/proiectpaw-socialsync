import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPassComponent } from './reset-pass.component';

describe('ResetPassComponent', () => {
  let component: ResetPassComponent;
  let fixture: ComponentFixture<ResetPassComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResetPassComponent]
    });
    fixture = TestBed.createComponent(ResetPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});