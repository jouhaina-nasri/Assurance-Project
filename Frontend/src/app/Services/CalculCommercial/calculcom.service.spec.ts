import { TestBed } from '@angular/core/testing';

import { CalculcomService } from './calculcom.service';

describe('CalculcomService', () => {
  let service: CalculcomService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CalculcomService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
