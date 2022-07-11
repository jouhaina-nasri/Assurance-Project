import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencefermeeComponent } from './agencefermee.component';

describe('AgencefermeeComponent', () => {
  let component: AgencefermeeComponent;
  let fixture: ComponentFixture<AgencefermeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgencefermeeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgencefermeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
