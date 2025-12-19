import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ViewAnnouncements } from './view-announcements';

describe('ViewAnnouncements', () => {
  let component: ViewAnnouncements;
  let fixture: ComponentFixture<ViewAnnouncements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewAnnouncements],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewAnnouncements);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load announcements on init', () => {
    expect(component.announcements.length).toBeGreaterThan(0);
  });

  it('should filter announcements by search term', () => {
    component.searchTerm = 'Assignment';
    component.onSearchChange();
    expect(component.filteredAnnouncements.length).toBeGreaterThan(0);
    expect(component.filteredAnnouncements.every(a => 
      a.title.toLowerCase().includes('assignment') || 
      a.message.toLowerCase().includes('assignment')
    )).toBeTruthy();
  });

  it('should filter announcements by course', () => {
    component.selectedCourseFilter = '1';
    component.onCourseFilterChange();
    expect(component.filteredAnnouncements.every(a => a.course === '1')).toBeTruthy();
  });

  it('should clear filters', () => {
    component.searchTerm = 'test';
    component.selectedCourseFilter = '1';
    component.clearFilters();
    expect(component.searchTerm).toBe('');
    expect(component.selectedCourseFilter).toBe('');
    expect(component.filteredAnnouncements.length).toBe(component.announcements.length);
  });

  it('should get unique courses', () => {
    const courses = component.uniqueCourses;
    expect(courses.length).toBeGreaterThan(0);
    const courseCodes = courses.map(c => c.courseCode);
    expect(new Set(courseCodes).size).toBe(courseCodes.length);
  });
});
