import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunityEditComponent } from './community-edit.component';

describe('CommunityEditComponent', () => {
  let component: CommunityEditComponent;
  let fixture: ComponentFixture<CommunityEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommunityEditComponent]
    });
    fixture = TestBed.createComponent(CommunityEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
});

