import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Material {
  id: number;
  title: string;
  type: string;
  size: string;
  icon: string;
  iconColor: string;
  courseCode: string;
}

interface CourseSection {
  courseCode: string;
  courseName: string;
  borderColor: string;
  materials: Material[];
}

@Component({
  selector: 'app-view-materials',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './view-materials.html',
  styleUrl: './view-materials.css',
})
export class ViewMaterials implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);

  sections: CourseSection[] = [];
  filteredSections: CourseSection[] = [];
  searchQuery = '';
  selectedCourse = '';
  selectedType = '';

  ngOnInit() {
    this.loadMaterials();

    // Check for query params from My Courses
    this.route.queryParams.subscribe(params => {
      if (params['course']) {
        this.selectedCourse = params['course'];
        this.filterMaterials();
      }
    });
  }

  loadMaterials() {
    this.sections = [
      {
        courseCode: 'CS101',
        courseName: 'Intro to Programming',
        borderColor: 'blue-border',
        materials: [
          {
            id: 1,
            title: 'Week 1 - Introduction to Python',
            type: 'Lecture Slides',
            size: 'PDF, 2.5 MB',
            icon: 'slideshow',
            iconColor: 'primary-icon',
            courseCode: 'CS101',
          },
          {
            id: 2,
            title: 'Reading: Chapter 1 & 2',
            type: 'Reading Material',
            size: 'Online Link',
            icon: 'description',
            iconColor: 'green-icon',
            courseCode: 'CS101',
          },
          {
            id: 3,
            title: 'Problem Set 1',
            type: 'Problem Set',
            size: 'PDF, 300 KB',
            icon: 'quiz',
            iconColor: 'red-icon',
            courseCode: 'CS101',
          },
        ],
      },
      {
        courseCode: 'DS310',
        courseName: 'Web Development',
        borderColor: 'pink-border',
        materials: [
          {
            id: 4,
            title: 'Course Syllabus',
            type: 'Syllabus',
            size: 'PDF, 1.2 MB',
            icon: 'menu_book',
            iconColor: 'purple-icon',
            courseCode: 'DS310',
          },
          {
            id: 5,
            title: 'Week 5 - React Hooks',
            type: 'Lecture Slides',
            size: 'PPTX, 5.1 MB',
            icon: 'slideshow',
            iconColor: 'primary-icon',
            courseCode: 'DS310',
          },
          {
            id: 6,
            title: 'Project 2 Starter Code',
            type: 'Code Files',
            size: 'ZIP, 850 KB',
            icon: 'code',
            iconColor: 'amber-icon',
            courseCode: 'DS310',
          },
        ],
      },
    ];
    this.filterMaterials();
  }

  filterMaterials() {
    let filtered = [...this.sections];

    // Filter by course
    if (this.selectedCourse) {
      filtered = filtered.filter(s => s.courseCode === this.selectedCourse);
    }

    // Filter by type
    if (this.selectedType) {
      filtered = filtered.map(section => ({
        ...section,
        materials: section.materials.filter(m => m.type === this.selectedType),
      }));
    }

    // Search
    if (this.searchQuery) {
      const query = this.searchQuery.toLowerCase();
      filtered = filtered.map(section => ({
        ...section,
        materials: section.materials.filter(
          m =>
            m.title.toLowerCase().includes(query) ||
            m.type.toLowerCase().includes(query)
        ),
      }));
    }

    this.filteredSections = filtered.filter(s => s.materials.length > 0);
  }

  onSearchChange() {
    this.filterMaterials();
  }

  onFilterChange() {
    this.filterMaterials();
  }

  getCourseOptions(): string[] {
    const courses = new Set(['All Courses']);
    this.sections.forEach(section => {
      courses.add(section.courseCode);
    });
    return Array.from(courses);
  }

  getMaterialTypes(): string[] {
    const types = new Set(['All Types']);
    this.sections.forEach(section => {
      section.materials.forEach(material => {
        types.add(material.type);
      });
    });
    return Array.from(types);
  }

  downloadMaterial(material: Material): void {
    console.log('Downloading:', material.title);
    // Simulate download
    const link = document.createElement('a');
    link.href = '#';
    link.download = material.title;
    link.click();
    alert(`Downloading: ${material.title}`);
  }

  viewMaterial(material: Material): void {
    console.log('Viewing:', material.title);
    alert(`Opening: ${material.title}\n\nType: ${material.type}`);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
