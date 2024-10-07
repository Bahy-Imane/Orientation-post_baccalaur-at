import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldOfStudyHomeComponent } from './field-of-study-home.component';

describe('FieldOfStudyHomeComponent', () => {
  let component: FieldOfStudyHomeComponent;
  let fixture: ComponentFixture<FieldOfStudyHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldOfStudyHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FieldOfStudyHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
