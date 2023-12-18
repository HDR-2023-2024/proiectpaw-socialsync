import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityCreateComponent } from './community-create.component';

describe('CommunityCreateComponent', () => {
  let component: CommunityCreateComponent;
  let fixture: ComponentFixture<CommunityCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommunityCreateComponent]
    });
    fixture = TestBed.createComponent(CommunityCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});