import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

interface Material {
  id: number;
  course: string;
  title: string;
  type: 'lecture' | 'reading' | 'problem' | 'syllabus';
  size: string;
  uploadDate: Date;
}

@Component({
  selector: 'app-student-materials',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './student-materials.html',
  styleUrls: ['./student-materials.css']
})
export class StudentMaterialsComponent implements OnInit {
  selectedCourse: string = 'all';
  selectedType: string = 'all';
  searchTerm: string = '';

  materials: Material[] = [
    { id: 1, course: 'CS101', title: 'Week 1 - Introduction to Python', type: 'lecture', size: '2.5 MB', uploadDate: new Date(2024, 8, 1) },
    { id: 2, course: 'CS101', title: 'Reading: Chapter 1 & 2', type: 'reading', size: '300 KB', uploadDate: new Date(2024, 8, 1) },
    { id: 3, course: 'CS101', title: 'Problem Set 1', type: 'problem', size: '150 KB', uploadDate: new Date(2024, 8, 3) },
    { id: 4, course: 'MATH201', title: 'Calculus II Syllabus', type: 'syllabus', size: '1.2 MB', uploadDate: new Date(2024, 8, 5) },
    { id: 5, course: 'MATH201', title: 'Week 5 - React Hooks', type: 'lecture', size: '5.1 MB', uploadDate: new Date(2024, 8, 15) },
  ];

  filteredMaterials: Material[] = [];
  courses: string[] = [];

  ngOnInit() {
    this.courses = ['all', ...new Set(this.materials.map(m => m.course))];
    this.filterMaterials();
  }

  filterMaterials() {
    this.filteredMaterials = this.materials.filter(m => {
      const coursMatch = this.selectedCourse === 'all' || m.course === this.selectedCourse;
      const typeMatch = this.selectedType === 'all' || m.type === this.selectedType;
      const searchMatch = m.title.toLowerCase().includes(this.searchTerm.toLowerCase());
      return coursMatch && typeMatch && searchMatch;
    });
  }

  getTypeIcon(type: string): string {
    const icons: { [key: string]: string } = {
      'lecture': 'description',
      'reading': 'library_books',
      'problem': 'assignment',
      'syllabus': 'menu_book'
    };
    return icons[type] || 'description';
  }

  downloadFile(material: Material) {
    console.log('Downloading:', material.title);
  }
}
