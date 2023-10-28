import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullPostComponent } from './full-post.component';

describe('FullPostComponent', () => {
  let component: FullPostComponent;
  let fixture: ComponentFixture<FullPostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FullPostComponent]
    });
    fixture = TestBed.createComponent(FullPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
