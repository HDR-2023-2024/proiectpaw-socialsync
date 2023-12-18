import { TestBed } from '@angular/core/testing';

import { FullPostService } from './full-post.service';

describe('FullPostService', () => {
  let service: FullPostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FullPostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
