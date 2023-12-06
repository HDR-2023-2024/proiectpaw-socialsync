import { TestBed } from '@angular/core/testing';

import { CreatAccountService } from './creat-account.service';

describe('CreatAccountService', () => {
  let service: CreatAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreatAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
