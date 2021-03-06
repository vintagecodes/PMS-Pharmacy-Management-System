import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { DrugsService } from './drugs.service';

describe('DrugsService', () => {
  let service: DrugsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpClientModule]
    });
    service = TestBed.inject(DrugsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
