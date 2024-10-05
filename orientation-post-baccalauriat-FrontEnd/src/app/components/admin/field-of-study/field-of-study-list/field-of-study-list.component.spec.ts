import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FieldOfStudyListComponent } from './field-of-study-list.component';

describe('FieldOfStudyListComponent', () => {
  let component: FieldOfStudyListComponent;
  let fixture: ComponentFixture<FieldOfStudyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FieldOfStudyListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FieldOfStudyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
