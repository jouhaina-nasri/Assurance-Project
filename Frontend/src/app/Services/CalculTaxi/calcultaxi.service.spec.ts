import { TestBed } from '@angular/core/testing';

import { CalcultaxiService } from './calcultaxi.service';

describe('CalcultaxiService', () => {
  let service: CalcultaxiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CalcultaxiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
