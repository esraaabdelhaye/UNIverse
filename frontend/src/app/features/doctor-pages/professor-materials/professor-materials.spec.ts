import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorMaterials } from './professor-materials';

describe('ProfessorMaterials', () => {
  let component: ProfessorMaterials;
  let fixture: ComponentFixture<ProfessorMaterials>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorMaterials]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorMaterials);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
