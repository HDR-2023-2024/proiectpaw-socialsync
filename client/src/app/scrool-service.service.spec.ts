import { TestBed } from '@angular/core/testing';

import { ScroolServiceService } from './scrool-service.service';

describe('ScroolServiceService', () => {
  let service: ScroolServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScroolServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
