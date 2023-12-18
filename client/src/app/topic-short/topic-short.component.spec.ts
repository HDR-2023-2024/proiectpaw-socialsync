import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicShortComponent } from './topic-short.component';

describe('TopicShortComponent', () => {
  let component: TopicShortComponent;
  let fixture: ComponentFixture<TopicShortComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopicShortComponent]
    });
    fixture = TestBed.createComponent(TopicShortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
