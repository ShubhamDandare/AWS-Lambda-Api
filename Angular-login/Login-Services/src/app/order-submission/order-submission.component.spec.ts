import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderSubmissionComponent } from './order-submission.component';

describe('OrderSubmissionComponent', () => {
  let component: OrderSubmissionComponent;
  let fixture: ComponentFixture<OrderSubmissionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderSubmissionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderSubmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
