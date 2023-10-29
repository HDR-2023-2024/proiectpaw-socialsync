import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullPostPageComponent } from './full-post-page.component';

describe('FullPostPageComponent', () => {
  let component: FullPostPageComponent;
  let fixture: ComponentFixture<FullPostPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FullPostPageComponent]
    });
    fixture = TestBed.createComponent(FullPostPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
