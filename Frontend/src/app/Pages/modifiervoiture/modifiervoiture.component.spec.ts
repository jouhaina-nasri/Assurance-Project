import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifiervoitureComponent } from './modifiervoiture.component';

describe('ModifiervoitureComponent', () => {
  let component: ModifiervoitureComponent;
  let fixture: ComponentFixture<ModifiervoitureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifiervoitureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifiervoitureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
