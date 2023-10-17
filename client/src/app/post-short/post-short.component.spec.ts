import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostShortComponent } from './post-short.component';

describe('PostShortComponent', () => {
  let component: PostShortComponent;
  let fixture: ComponentFixture<PostShortComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostShortComponent]
    });
    fixture = TestBed.createComponent(PostShortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
