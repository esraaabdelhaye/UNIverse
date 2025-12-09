import { Component, OnInit } from '@angular/core';

interface Faculty {
  id: number;
  name: string;
  email: string;
  department: string;
  position: 'Professor' | 'Associate Professor' | 'Lecturer' | 'TA';
  coursesTaught: number;
  status: 'active' | 'inactive' | 'on-leave';
}

@Component({
  selector: 'app-admin-faculty',
  templateUrl: './admin-faculty.component.html',
  styleUrls: ['./admin-faculty.component.css']
})
export class AdminFacultyComponent implements OnInit {
  searchTerm: string = '';
  selectedDept: string = 'all';
  selectedPosition: string = 'all';

  faculty: Faculty[] = [
    { id: 1, name: 'Dr. Jane Smith', email: 'jsmith@university.edu', department: 'History', position: 'Professor', coursesTaught: 3, status: 'active' },
    { id: 2, name: 'Prof. Robert Downy', email: 'rdowny@university.edu', department: 'Computer Science', position: 'Associate Professor', coursesTaught: 4, status: 'active' },
    { id: 3, name: 'Alice Johnson', email: 'ajohnson@university.edu', department: 'Mathematics', position: 'Lecturer', coursesTaught: 2, status: 'inactive' },
    { id: 4, name: 'Michael Chen', email: 'mchen@university.edu', department: 'Computer Science', position: 'TA', coursesTaught: 5, status: 'active' },
    { id: 5, name: 'Dr. Emily White', email: 'ewhite@university.edu', department: 'History', position: 'Professor', coursesTaught: 3, status: 'on-leave' },
  ];

  filteredFaculty: Faculty[] = [];
  departments = ['all', 'Computer Science', 'Mathematics', 'History', 'Physics', 'Chemistry'];
  positions = ['all', 'Professor', 'Associate Professor', 'Lecturer', 'TA'];

  ngOnInit() {
    this.filterFaculty();
  }

  filterFaculty() {
    this.filteredFaculty = this.faculty.filter(f => {
      const searchMatch = f.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        f.email.toLowerCase().includes(this.searchTerm.toLowerCase());
      const deptMatch = this.selectedDept === 'all' || f.department === this.selectedDept;
      const posMatch = this.selectedPosition === 'all' || f.position === this.selectedPosition;
      return searchMatch && deptMatch && posMatch;
    });
  }

  editFaculty(member: Faculty) {
    console.log('Edit:', member);
  }

  deleteFaculty(member: Faculty) {
    if (confirm('Are you sure you want to delete this faculty member?')) {
      this.faculty = this.faculty.filter(f => f.id !== member.id);
      this.filterFaculty();
    }
  }

  viewProfile(member: Faculty) {
    console.log('View profile:', member);
  }

  getStatusColor(status: string): string {
    const colors: { [key: string]: string } = {
      'active': '#4CAF50',
      'inactive': '#BDBDBD',
      'on-leave': '#FF9800'
    };
    return colors[status] || '#26A69A';
  }
}
