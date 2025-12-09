import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface FacultyMember {
  id: string;
  name: string;
  email: string;
  employeeId: string;
  department: string;
  position: string;
  courseCount: number;
  status: 'Active' | 'Inactive' | 'On Leave';
  avatar: string;
}

interface PaginationInfo {
  currentPage: number;
  pageSize: number;
  totalItems: number;
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

  facultyMembers: FacultyMember[] = [
    {
      id: '1',
      name: 'Dr. Jane Smith',
      email: 'jsmith@universe.edu',
      employeeId: 'EMP45678',
      department: 'History',
      position: 'Professor',
      courseCount: 3,
      status: 'Active',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuBjNVIwYogETpglvkEd7kd4IK1K9-HPRNlkOroRXJaVZAv_Oxkr_flu4Oukr96laOwcdk0IardreU7Nw0DRGA4hyFZqrw2oklKovs9QaMzczv58zvToVUEkJR-BLly6VCWvf6XdifTblP-cki6h9MeI4IhtaKAapwoUL2mg4XHPX8L8OOrpKpUz_Rui88Te6_WWBWKph57tSD8S80N27dMPl1XkCzR5QruN3VqnEqVlMTHL-oBBCuyop1DFVx_vXZLpqS21JdDCXhU',
    },
    {
      id: '2',
      name: 'Dr. Robert Downy',
      email: 'rdowny@universe.edu',
      employeeId: 'EMP12345',
      department: 'Computer Science',
      position: 'Associate Professor',
      courseCount: 4,
      status: 'Active',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuB3YHbEbU8SfmFn-oiqCV9_9rifN3FvAKK74-w9szwjG03WDWaZxLHLEt5ulxW4Iw6geddE7lDpg_wOUw0ZqPZMC8eFGLDRa5nl6Z8By1PyDy4uW6VtUGsrxQIwn6JHVgczTcJFMVjA13g56JGKU0xnwLHSCCekH3RagiV_GWlkWSSvE8DD86sOKb_Mtgf21I_RFzDelsA-LFCJMvNq0LRhANlqvShCpm3ofkCw66SFJjx0eZB13uoUgPad_7TmrkZ36pL4B8oG_wc',
    },
    {
      id: '3',
      name: 'Alice Johnson',
      email: 'ajohnson@universe.edu',
      employeeId: 'EMP91011',
      department: 'Mathematics',
      position: 'Lecturer',
      courseCount: 2,
      status: 'Inactive',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuAQYnUQkkLVgRasdG2jShDmaGSmxmKaZxWaODLOORMmJad27MJI2eznDU_8goc9ENNT07WfQQLhfOoJz78EfsRexVhZVwofkLVthNvUDPsY0UGJ_Zd-sKG0z_Iz30OFTO2tXa6wXGbvyb3U2ow8Vg7BjGLhmB9oJiYNelkDIowVBze473F68tXd1-HcWxcmLPuXt_WyvGEawSh-URY-Mne1RptdSJnibxb6Ke4CYpb73LRtCIKzGQ-nHykjK-vLbNq0TwwEagb7_LQ',
    },
    {
      id: '4',
      name: 'Michael Chen',
      email: 'mchen@universe.edu',
      employeeId: 'EMP12131',
      department: 'Computer Science',
      position: 'Teaching Assistant',
      courseCount: 5,
      status: 'Active',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuACKLE-qZUd1bqas4x8BUBex-goyY-X4PVm5dlYvjACscg4DFj3Ads23iuo8vu_XATO-0mx8dydToMsaLhN8nLw6uFGpWaJ5p_HdRc5yZLd8s1OAYIusAMHU42bxp9_TXhCzbVfXxknjVyBDC7Xt38xIUA0H2vYSFIIcbPCkp1-ycTY_Y-ibtHktY9TJS_pdJY37TeStiaLBpaX0EnAIh5MflM7rYZzkrNaP-Z-JzD7ABZO47xpghDCE_PK1Zd2RMapCvVpoLnTTaU',
    },
    {
      id: '5',
      name: 'Dr. Emily White',
      email: 'ewhite@universe.edu',
      employeeId: 'EMP41516',
      department: 'History',
      position: 'Senior Lecturer',
      courseCount: 3,
      status: 'On Leave',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuB3O896A0bUMf3i4mDiLfIQZsbfm1BhFmIKAhLBgtkiKuhVyfZEJat_BAtxOG1OLtN_BiRp65KHrrRnG5S1EvlAoW_Fe6PC2tvV88azn2ARi6YzaKK6D3BX8GmdVzxas10NAatkHJGM4GkaL3x8tBCs-epMKmwMU5xyJAlp6VVby0vK7wNwlI4BP5wpiLWKBY5PW0a1mAEFRphetl3A0Io0alLSWBFfOXqi3p6uCpMENmw_TU3pWqT5PUCAxoY3Iz21Ozb1QOqG9sU',
    },
  ];

  filteredFaculty: FacultyMember[] = [];
  searchTerm: string = '';
  selectedDepartment: string = 'Department';
  selectedPosition: string = 'Position';

  pagination: PaginationInfo = {
    currentPage: 1,
    pageSize: 5,
    totalItems: 25,
  };

  departments: string[] = [
    'Department',
    'Computer Science',
    'History',
    'Mathematics',
  ];

  positions: string[] = [
    'Position',
    'Professor',
    'Associate Professor',
    'Lecturer',
    'Teaching Assistant',
    'Senior Lecturer',
  ];

  ngOnInit(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredFaculty = this.facultyMembers.filter((faculty) => {
      const matchesSearch =
        faculty.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        faculty.employeeId
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        faculty.email.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesDepartment =
        this.selectedDepartment === 'Department' ||
        faculty.department === this.selectedDepartment;

      const matchesPosition =
        this.selectedPosition === 'Position' ||
        faculty.position === this.selectedPosition;

      return matchesSearch && matchesDepartment && matchesPosition;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  addFaculty(): void {
    console.log('Add faculty clicked');
    // Implement add faculty logic - navigate to add faculty form
  }

  editFaculty(faculty: FacultyMember): void {
    console.log('Edit faculty:', faculty.name);
    // Implement edit faculty logic
  }

  viewMore(faculty: FacultyMember): void {
    console.log('Show more options for:', faculty.name);
    // Implement context menu or modal for more actions
  }

  editFacultyDetails(faculty: FacultyMember): void {
    console.log('Opening edit details modal for:', faculty.name);
    // Implement edit details logic
  }

  assignCourses(faculty: FacultyMember): void {
    console.log('Assigning courses to:', faculty.name);
    // Implement course assignment logic
  }

  viewPerformance(faculty: FacultyMember): void {
    console.log('Viewing performance for:', faculty.name);
    // Implement performance view logic
  }

  deactivateAccount(faculty: FacultyMember): void {
    if (
      confirm(
        `Are you sure you want to deactivate ${faculty.name}'s account? This action can be reversed later.`
      )
    ) {
      console.log('Deactivating account for:', faculty.name);
      faculty.status = 'Inactive';
      // Implement deactivate logic
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'Active':
        return 'green';
      case 'Inactive':
        return 'red';
      case 'On Leave':
        return 'yellow';
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
}
