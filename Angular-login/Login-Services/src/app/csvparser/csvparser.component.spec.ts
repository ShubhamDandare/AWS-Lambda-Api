import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsvparserComponent } from './csvparser.component';

describe('CsvparserComponent', () => {
  let component: CsvparserComponent;
  let fixture: ComponentFixture<CsvparserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsvparserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CsvparserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
