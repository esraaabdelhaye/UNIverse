import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { CourseService } from '../../../core/services/course.service';

// Using ‘any’ for Course temporarily to match backend structure flexibility or import simple interface
export interface Course {
  id?: number;
  courseCode: string;
  name: string;
  departmentName?: string; // For display
  department?: any; // For payload
  creditHours: number;
  description?: string;
  semester?: string;
  year?: string;
  status?: string;
  capacity?: number;
  enrolled?: number;
}

@Component({
  selector: 'app-course-availability',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './course-availability.html',
  styleUrl: './course-availability.css',
})
export class CourseAvailability implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private courseService = inject(CourseService);
  // private departmentService = inject(DepartmentService);

  Math = Math;

  // Data State
  courses: any[] = [];
  filteredCourses: any[] = [];

  // Filters
  searchTerm: string = '';
  selectedDepartment: string = 'All Departments';
  selectedSemester: string = 'All Semesters';
  selectedStatus: string = 'All Statuses';

  // Dropdown Options
  departments: string[] = ['All Departments', 'Computer Science', 'Mathematics', 'Physics', 'History']; // Can be dynamic
  semesters: string[] = ['All Semesters', 'Fall 2024', 'Spring 2025'];
  statuses: string[] = ['All Statuses', 'Open', 'Closed', 'Full'];

  // Pagination
  pagination = {
    currentPage: 1,
    pageSize: 10,
    totalItems: 0,
  };

  // Modal State
  isModalOpen: boolean = false;
  isEditMode: boolean = false;
  currentCourse: any = {
    courseCode: '',
    name: '',
    department: null, // ID or Object
    creditHours: 3,
    semester: 'Fall 2024',
    year: 'Year 1',
    status: 'Open',
    capacity: 50
  };

  isLoading: boolean = false;

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses() {
    this.isLoading = true;
    this.courseService.getAllCourses(this.pagination.currentPage - 1, this.pagination.pageSize).subscribe({
      next: (res: any) => {
        this.isLoading = false;
        let incomingCourses = [];
        if (res.data && res.data.content) {
          incomingCourses = res.data.content;
          this.pagination.totalItems = res.data.totalElements;
        } else if (Array.isArray(res.data)) {
          incomingCourses = res.data;
          this.pagination.totalItems = res.data.length;
        }

        // Map DTO to Local Model
        this.courses = incomingCourses.map((dto: any) => ({
          id: dto.id, // Strict ID from backend. DO NOT Fallback to courseCode.
          courseCode: dto.courseCode,
          name: dto.courseTitle,
          departmentName: dto.department,
          creditHours: dto.credits,
          semester: dto.semester,
          capacity: dto.capacity,
          status: dto.status || 'Open',
          enrolled: dto.enrolled,
          description: dto.description
        }));

        console.log("Loaded Courses:", this.courses);

        this.applyFilters();
      },
      error: (err) => {
        this.isLoading = false;
        console.error("Failed to load courses", err);
      }
    });
  }

  applyFilters(): void {
    // Client-side filtering for simplicity if API doesn't support complex composite search yet
    // Or we can rely on backend search. For now, client-side filtering on the current page data

    this.filteredCourses = this.courses.filter((course) => {
      const matchesSearch =
        (course.courseCode?.toLowerCase() || '').includes(this.searchTerm.toLowerCase()) ||
        (course.name?.toLowerCase() || '').includes(this.searchTerm.toLowerCase());

      const matchesDepartment =
        this.selectedDepartment === 'All Departments' ||
        course.departmentName === this.selectedDepartment;

      const matchesSemester =
        this.selectedSemester === 'All Semesters' ||
        course.semester === this.selectedSemester;

      return matchesSearch && matchesDepartment && matchesSemester;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  openAddModal() {
    this.isEditMode = false;
    this.currentCourse = {
      courseCode: '',
      name: '',
      creditHours: 3,
      semester: 'Fall 2024',
      year: 'Year 1',
      capacity: 50,
      department: { id: 1 } // Default or Selectable
    };
    this.isModalOpen = true;
  }

  openEditModal(course: any) {
    console.log('📝 Edit Modal Clicked for:', course);
    if (!course.id) {
      console.error('❌ Course ID missing:', course);
      alert("System Update Required: This course is missing its ID. Please RESTART YOUR BACKEND SERVER to apply recent schema changes.");
      return;
    }
    this.isEditMode = true;
    this.currentCourse = { ...course }; // Clone
    console.log('✅ Current Course for Edit:', this.currentCourse);
    console.log('📋 Department Value:', this.currentCourse.departmentName);
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  saveCourse() {
    // ... payload construction ...
    const payload = {
      courseCode: this.currentCourse.courseCode,
      courseTitle: this.currentCourse.name,
      department: this.currentCourse.departmentName || 'Computer Science',
      credits: this.currentCourse.creditHours,
      semester: this.currentCourse.semester,
      capacity: this.currentCourse.capacity,
      description: this.currentCourse.description,
      status: this.currentCourse.status || 'Open'
    };

    console.log("Saving Course Payload:", payload); // Debug

    if (this.isEditMode) {
      if (!this.currentCourse.id) {
        alert("Error: Course ID missing. Please restart backend.");
        return;
      }
      this.courseService.updateCourse(this.currentCourse.id, payload).subscribe({
        next: () => {
          alert("Course Updated Successfully");
          
          // Update the course in the local arrays (frontend only)
          const updatedCourse = {
            id: this.currentCourse.id,
            courseCode: this.currentCourse.courseCode,
            name: this.currentCourse.name,
            departmentName: this.currentCourse.departmentName, // This will update!
            creditHours: this.currentCourse.creditHours,
            semester: this.currentCourse.semester,
            capacity: this.currentCourse.capacity,
            status: this.currentCourse.status || 'Open',
            description: this.currentCourse.description
          };
          
          // Update in main courses array
          const courseIndex = this.courses.findIndex(c => c.id === this.currentCourse.id);
          if (courseIndex !== -1) {
            this.courses[courseIndex] = { ...this.courses[courseIndex], ...updatedCourse };
          }
          
          // Re-apply filters to update filtered view
          this.applyFilters();
          
          this.closeModal();
        },
        error: (err) => alert("Failed to update course: " + (err.error?.message || err.message))
      });
    } else {
      // ... create logic ...
      this.courseService.createCourse(payload).subscribe({
        next: () => {
          alert("Course Created Successfully");
          this.closeModal();
          this.loadCourses();
        },
        error: (err) => alert("Failed to create course: " + (err.error?.message || err.message))
      });
    }
  }

  deleteCourse(course: any) {
    console.log('🗑️ Delete Clicked for:', course);
    if (!course.id) {
      console.error('❌ Course ID missing:', course);
      alert("System Update Required: This course is missing its ID. Please RESTART YOUR BACKEND SERVER to apply recent schema changes.");
      return;
    }
    if (confirm(`Are you sure you want to delete ${course.courseCode}?`)) {
      console.log('✅ Deleting course ID:', course.id);
      this.courseService.deleteCourse(course.id).subscribe({
        next: () => {
          console.log('✅ Delete successful');
          alert("Course deleted");
          this.loadCourses();
        },
        error: (err) => {
          console.error('❌ Delete failed:', err);
          alert("Failed to delete: " + (err.error?.message || err.message));
        }
      });
    } else {
      console.log('❌ Delete cancelled by user');
    }
  }

  // Pagination
  goToPage(page: number): void {
    this.pagination.currentPage = page;
    this.loadCourses();
  }

  previousPage(): void {
    if (this.pagination.currentPage > 1) {
      this.pagination.currentPage--;
      this.loadCourses();
    }
  }

  nextPage(): void {
    const totalPages = Math.ceil(this.pagination.totalItems / this.pagination.pageSize);
    if (this.pagination.currentPage < totalPages) {
      this.pagination.currentPage++;
      this.loadCourses();
    }
  }

  // Toggle course status between Open/Closed
  toggleCourseStatus(course: any): void {
    if (!course.id) {
      alert('Course ID missing. Please reload the page.');
      return;
    }
    const newStatus = course.status === 'Open' ? 'Closed' : 'Open';
    this.isLoading = true;
    this.courseService.updateCourseStatus(course.id, newStatus).subscribe({
      next: () => {
        course.status = newStatus;
        this.isLoading = false;
        // Update in local array as well
        this.applyFilters();
      },
      error: (err: any) => {
        this.isLoading = false;
        alert('Failed to update status: ' + (err.error?.message || err.message));
      }
    });
  }

  // Utility
  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'Open': return 'green';
      case 'Full': return 'yellow';
      case 'Closed': return 'red';
      default: return '';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
