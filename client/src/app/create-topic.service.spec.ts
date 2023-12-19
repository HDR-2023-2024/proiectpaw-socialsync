import { TestBed } from '@angular/core/testing';

import { CreateTopicService } from './create-topic.service';

describe('CreateTopicService', () => {
  let service: CreateTopicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateTopicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
