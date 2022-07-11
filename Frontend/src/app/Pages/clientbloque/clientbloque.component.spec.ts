import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientbloqueComponent } from './clientbloque.component';

describe('ClientbloqueComponent', () => {
  let component: ClientbloqueComponent;
  let fixture: ComponentFixture<ClientbloqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientbloqueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientbloqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
