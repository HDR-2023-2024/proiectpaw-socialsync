import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestareStorageComponent } from './testare-storage.component';

describe('TestareStorageComponent', () => {
  let component: TestareStorageComponent;
  let fixture: ComponentFixture<TestareStorageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TestareStorageComponent]
    });
    fixture = TestBed.createComponent(TestareStorageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
