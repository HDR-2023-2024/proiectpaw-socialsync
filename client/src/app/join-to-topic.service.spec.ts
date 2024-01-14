import { TestBed } from '@angular/core/testing';

import { JoinToTopicService } from './join-to-topic.service';

describe('JoinToTopicService', () => {
  let service: JoinToTopicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JoinToTopicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
