import { TestBed } from '@angular/core/testing';

import { DataHomeService } from './data-home.service';

describe('DataHomeService', () => {
  let service: DataHomeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataHomeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
