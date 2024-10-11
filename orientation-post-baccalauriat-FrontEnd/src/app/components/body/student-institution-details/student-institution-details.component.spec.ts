import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentInstitutionDetailsComponent } from './student-institution-details.component';

describe('StudentInstitutionDetailsComponent', () => {
  let component: StudentInstitutionDetailsComponent;
  let fixture: ComponentFixture<StudentInstitutionDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentInstitutionDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentInstitutionDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
