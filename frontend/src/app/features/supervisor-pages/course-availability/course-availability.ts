import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Course {
  id: string;
  code: string;
  title: string;
  department: string;
  instructor: string;
  capacity: number;
  enrolled: number;
  status: 'Open' | 'Closed' | 'Full';
}

interface PaginationInfo {
  currentPage: number;
  pageSize: number;
  totalItems: number;
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

  courses: Course[] = [
    {
      id: '1',
      code: 'CS101',
      title: 'Introduction to Programming',
      department: 'Computer Science',
      instructor: 'Dr. Alice Johnson',
      capacity: 120,
      enrolled: 118,
      status: 'Open',
    },
    {
      id: '2',
      code: 'HIST203',
      title: 'Modern World History',
      department: 'History',
      instructor: 'Dr. Jane Smith',
      capacity: 80,
      enrolled: 80,
      status: 'Full',
    },
    {
      id: '3',
      code: 'MATH201',
      title: 'Calculus II',
      department: 'Mathematics',
      instructor: 'Dr. Robert Chen',
      capacity: 100,
      enrolled: 95,
      status: 'Open',
    },
    {
      id: '4',
      code: 'PHYS110',
      title: 'Introductory Physics',
      department: 'Physics',
      instructor: 'Unassigned',
      capacity: 75,
      enrolled: 0,
      status: 'Closed',
    },
  ];

  filteredCourses: Course[] = [];
  searchTerm: string = '';
  selectedDepartment: string = 'All Departments';
  selectedSemester: string = 'All Semesters';
  selectedStatus: string = 'All Statuses';

  pagination: PaginationInfo = {
    currentPage: 1,
    pageSize: 10,
    totalItems: 100,
  };

  departments: string[] = [
    'All Departments',
    'Computer Science',
    'History',
    'Mathematics',
  ];

  semesters: string[] = ['All Semesters', 'Fall 2024', 'Spring 2025'];

  statuses: string[] = ['All Statuses', 'Open', 'Closed', 'Full'];

  ngOnInit(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredCourses = this.courses.filter((course) => {
      const matchesSearch =
        course.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        course.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        course.instructor.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesDepartment =
        this.selectedDepartment === 'All Departments' ||
        course.department === this.selectedDepartment;

      const matchesStatus =
        this.selectedStatus === 'All Statuses' ||
        course.status === this.selectedStatus;

      return matchesSearch && matchesDepartment && matchesStatus;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  addNewCourse(): void {
    console.log('Add new course clicked');
    // Implement add course logic
  }

  editCourse(course: Course): void {
    console.log('Edit course:', course.code);
    // Implement edit course logic
  }

  addStudent(course: Course): void {
    console.log('Add student to course:', course.code);
    // Implement add student logic
  }

  cancelCourse(course: Course): void {
    if (confirm(`Are you sure you want to cancel ${course.code}?`)) {
      console.log('Cancel course:', course.code);
      // Implement cancel course logic
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'Open':
        return 'green';
      case 'Full':
        return 'yellow';
      case 'Closed':
        return 'red';
      default:
        return '';
    }
  }

  goToPage(page: number): void {
    this.pagination.currentPage = page;
    console.log('Navigate to page:', page);
  }

  previousPage(): void {
    if (this.pagination.currentPage > 1) {
      this.pagination.currentPage--;
    }
  }

  nextPage(): void {
    const totalPages = Math.ceil(
      this.pagination.totalItems / this.pagination.pageSize
    );
    if (this.pagination.currentPage < totalPages) {
      this.pagination.currentPage++;
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  protected readonly Math = Math;
}
