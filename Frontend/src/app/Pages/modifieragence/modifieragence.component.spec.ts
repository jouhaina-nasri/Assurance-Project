import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifieragenceComponent } from './modifieragence.component';

describe('ModifieragenceComponent', () => {
  let component: ModifieragenceComponent;
  let fixture: ComponentFixture<ModifieragenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifieragenceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifieragenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
