import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService, FacultyItem } from '../../../core/services/doctor.service';

interface FacultyDisplayItem {
  id: string;
  name: string;
  email: string;
  employeeId: string;
  department: string;
  position: string;
  courseCount: number;
  status: string;
  avatar: string;
}

@Component({
  selector: 'app-manage-faculty',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './manage-faculty.html',
  styleUrl: './manage-faculty.css',
})
export class ManageFaculty implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private doctorService = inject(DoctorService);

  // Data
  facultyMembers: FacultyDisplayItem[] = [];
  filteredFaculty: FacultyDisplayItem[] = [];

  // Filters
  searchTerm: string = '';
  selectedDepartment: string = 'All Departments';
  selectedPosition: string = 'All Positions';

  // Dropdown Options
  departments: string[] = ['All Departments', 'Computer Science', 'Mathematics', 'Physics', 'History'];
  positions: string[] = ['All Positions', 'Professor', 'Associate Professor', 'Assistant Professor', 'Lecturer'];

  // Pagination
  pagination = {
    currentPage: 1,
    pageSize: 10,
    totalItems: 0,
  };

  // Modal State
  isModalOpen: boolean = false;
  isEditMode: boolean = false;
  currentFaculty: any = this.getEmptyFaculty();

  isLoading: boolean = false;

  ngOnInit(): void {
    this.loadFaculty();
  }

  loadFaculty(): void {
    this.isLoading = true;
    this.doctorService.getAllDoctors(this.pagination.currentPage - 1, this.pagination.pageSize).subscribe({
      next: (res: any) => {
        console.log('📥 Full Response:', res); // DEBUG
        this.isLoading = false;
        let doctors: FacultyItem[] = [];
        if (res.data && res.data.content) {
          console.log('✅ Using res.data.content'); // DEBUG
          doctors = res.data.content;
          this.pagination.totalItems = res.data.totalElements;
        } else if (Array.isArray(res.data)) {
          console.log('✅ Using res.data (array)'); // DEBUG
          doctors = res.data;
          this.pagination.totalItems = res.data.length;
        } else {
          console.log('⚠️ Unknown response format'); // DEBUG
        }

        console.log('👥 Doctors array:', doctors); // DEBUG

        // Map DTO to display model
        this.facultyMembers = doctors.map((dto: FacultyItem) => ({
          id: dto.doctorId,
          name: dto.fullName,
          email: dto.email,
          employeeId: dto.doctorId,
          department: dto.department || 'Not Assigned',
          position: dto.specialization || 'Faculty',
          courseCount: dto.courseCount || 0,
          status: dto.active ? 'Active' : 'Inactive',
          avatar: 'https://ui-avatars.com/api/?name=' + encodeURIComponent(dto.fullName) + '&background=random'
        }));

        console.log('✅ Faculty Members:', this.facultyMembers); // DEBUG

        this.applyFilters();
      },
      error: (err: any) => {
        this.isLoading = false;
        console.error('Failed to load faculty', err);
      }
    });
  }

  applyFilters(): void {
    this.filteredFaculty = this.facultyMembers.filter((faculty) => {
      const matchesSearch =
        (faculty.name || '').toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        (faculty.employeeId || '').toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesDepartment =
        this.selectedDepartment === 'All Departments' ||
        faculty.department === this.selectedDepartment;

      const matchesPosition =
        this.selectedPosition === 'All Positions' ||
        faculty.position === this.selectedPosition;

      return matchesSearch && matchesDepartment && matchesPosition;
    });
    
    console.log('🔍 Filtered Faculty Count:', this.filteredFaculty.length); // DEBUG
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  // Modal Operations
  getEmptyFaculty(): any {
    return {
      doctorId: '',
      fullName: '',
      email: '',
      phoneNumber: '',
      department: 'Computer Science',
      specialization: 'Professor',
      officeLocation: '',
      qualifications: '',
      password: ''
    };
  }

  openAddModal(): void {
    this.isEditMode = false;
    this.currentFaculty = this.getEmptyFaculty();
    this.isModalOpen = true;
  }

  openEditModal(faculty: FacultyDisplayItem): void {
    if (!faculty || !faculty.id) {
      alert('Invalid faculty member');
      return;
    }
    this.isEditMode = true;
    this.currentFaculty = {
      doctorId: faculty.id,
      fullName: faculty.name,
      email: faculty.email,
      phoneNumber: faculty.employeeId,
      department: faculty.department || 'Computer Science',
      specialization: faculty.position || 'Professor',
      officeLocation: '',
      qualifications: '',
      password: '' // Not used in edit mode
    };
    this.isModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.currentFaculty = this.getEmptyFaculty();
  }

  saveFaculty(): void {
    // Validation
    if (!this.currentFaculty.fullName || !this.currentFaculty.email) {
      alert('Please fill in required fields (Name and Email)');
      return;
    }

    if (!this.isEditMode && !this.currentFaculty.password) {
      alert('Password is required for new faculty members');
      return;
    }

    // Prepare payload
    const payload: any = {
      fullName: this.currentFaculty.fullName,
      email: this.currentFaculty.email,
      phoneNumber: this.currentFaculty.phoneNumber || '',
      department: this.currentFaculty.department,
      specialization: this.currentFaculty.specialization,
      officeLocation: this.currentFaculty.officeLocation || '',
      qualifications: this.currentFaculty.qualifications || '',
      availableForConsultation: true,
      active: true
    };

    // Add password only for new faculty
    if (!this.isEditMode) {
      payload.password = this.currentFaculty.password;
    }

    if (this.isEditMode) {
      // Update existing faculty
      const id = parseInt(this.currentFaculty.doctorId, 10);
      if (isNaN(id)) {
        alert('Invalid faculty ID');
        return;
      }
      this.doctorService.updateDoctor(id, payload).subscribe({
        next: () => {
          alert('Faculty member updated successfully');
          
          // Update the faculty in the local arrays (frontend only)
          const updatedFaculty: FacultyDisplayItem = {
            id: this.currentFaculty.doctorId,
            name: this.currentFaculty.fullName,
            email: this.currentFaculty.email,
            employeeId: this.currentFaculty.phoneNumber,
            department: this.currentFaculty.department,
            position: this.currentFaculty.specialization,
            courseCount: 0, // Keep existing or update
            status: 'Active',
            avatar: 'https://ui-avatars.com/api/?name=' + encodeURIComponent(this.currentFaculty.fullName) + '&background=random'
          };
          
          // Update in main facultyMembers array
          const facultyIndex = this.facultyMembers.findIndex(f => f.id === this.currentFaculty.doctorId);
          if (facultyIndex !== -1) {
            this.facultyMembers[facultyIndex] = { ...this.facultyMembers[facultyIndex], ...updatedFaculty };
          }
          
          // Re-apply filters to update filtered view
          this.applyFilters();
          
          this.closeModal();
        },
        error: (err: any) => alert('Failed to update: ' + (err.error?.message || err.message))
      });
    } else {
      // Create new faculty
      this.doctorService.createDoctor(payload).subscribe({
        next: () => {
          alert('Faculty member added successfully');
          this.closeModal();
          this.loadFaculty(); // Reload to get fresh data from backend
        },
        error: (err: any) => {
          alert('Failed to create: ' + (err.error?.message || err.message));
        }
      });
    }
  }

  deleteFaculty(faculty: FacultyDisplayItem): void {
    if (!faculty || !faculty.id) {
      alert('Invalid faculty member');
      return;
    }
    if (confirm(`Are you sure you want to delete ${faculty.name}?`)) {
      const id = parseInt(faculty.id, 10);
      if (isNaN(id)) {
        alert('Invalid faculty ID');
        return;
      }
      this.doctorService.deleteDoctor(id).subscribe({
        next: () => {
          alert('Faculty member deleted successfully');
          
          // Remove from local arrays immediately
          const facultyIndex = this.facultyMembers.findIndex(f => f.id === faculty.id);
          if (facultyIndex !== -1) {
            this.facultyMembers.splice(facultyIndex, 1);
            this.pagination.totalItems--;
          }
          
          // Re-apply filters to update filtered view
          this.applyFilters();
        },
        error: (err: any) => alert('Failed to delete: ' + (err.error?.message || err.message))
      });
    }
  }

  // Quick Actions (kept for backward compatibility)
  viewMore(faculty: FacultyDisplayItem): void {
    this.openEditModal(faculty);
  }

  editFacultyDetails(faculty: FacultyDisplayItem): void {
    this.openEditModal(faculty);
  }

  assignCourses(faculty: FacultyDisplayItem): void {
    alert(`Assign courses to ${faculty.name} - Feature coming soon`);
  }

  viewPerformance(faculty: FacultyDisplayItem): void {
    alert(`View performance for ${faculty.name} - Feature coming soon`);
  }

  deactivateAccount(faculty: FacultyDisplayItem): void {
    this.deleteFaculty(faculty);
  }

  // Pagination
  goToPage(page: number): void {
    this.pagination.currentPage = page;
    this.loadFaculty();
  }

  previousPage(): void {
    if (this.pagination.currentPage > 1) {
      this.pagination.currentPage--;
      this.loadFaculty();
    }
  }

  nextPage(): void {
    const totalPages = Math.ceil(this.pagination.totalItems / this.pagination.pageSize);
    if (this.pagination.currentPage < totalPages) {
      this.pagination.currentPage++;
      this.loadFaculty();
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'Active': return 'badge-green';
      case 'Inactive': return 'badge-red';
      default: return '';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
