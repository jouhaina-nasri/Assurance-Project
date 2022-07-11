import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentbloqueComponent } from './agentbloque.component';

describe('AgentbloqueComponent', () => {
  let component: AgentbloqueComponent;
  let fixture: ComponentFixture<AgentbloqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentbloqueComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentbloqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
