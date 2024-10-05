import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldOfStudyFormComponent } from './field-of-study-form.component';

describe('FieldOfStudyFormComponent', () => {
  let component: FieldOfStudyFormComponent;
  let fixture: ComponentFixture<FieldOfStudyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldOfStudyFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FieldOfStudyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
