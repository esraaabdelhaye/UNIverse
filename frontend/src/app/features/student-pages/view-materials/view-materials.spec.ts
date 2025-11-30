import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMaterials } from './view-materials';

describe('ViewMaterials', () => {
  let component: ViewMaterials;
  let fixture: ComponentFixture<ViewMaterials>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewMaterials]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMaterials);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
