import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentsGroups } from './students-groups';

describe('StudentsGroups', () => {
  let component: StudentsGroups;
  let fixture: ComponentFixture<StudentsGroups>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentsGroups]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentsGroups);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
