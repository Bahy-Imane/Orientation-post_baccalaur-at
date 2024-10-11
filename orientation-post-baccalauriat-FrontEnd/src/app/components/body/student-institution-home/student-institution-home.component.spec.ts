import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentInstitutionHomeComponent } from './student-institution-home.component';

describe('StudentInstitutionHomeComponent', () => {
  let component: StudentInstitutionHomeComponent;
  let fixture: ComponentFixture<StudentInstitutionHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentInstitutionHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentInstitutionHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
