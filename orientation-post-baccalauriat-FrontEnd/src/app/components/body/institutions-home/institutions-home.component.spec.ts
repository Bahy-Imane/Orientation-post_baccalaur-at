import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstitutionsHomeComponent } from './institutions-home.component';

describe('InstitutionsHomeComponent', () => {
  let component: InstitutionsHomeComponent;
  let fixture: ComponentFixture<InstitutionsHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstitutionsHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InstitutionsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
