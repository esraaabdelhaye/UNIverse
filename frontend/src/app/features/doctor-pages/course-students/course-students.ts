import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { Student } from '../../../core/models/student.model';
import { CourseEnrollment } from '../../../core/models/enrollment.model';
import { CourseService } from '../../../core/services/course.service';
import { StudentService } from '../../../core/services/student.service';
import { MaterialService } from '../../../core/services/material.service';


interface Material {
  id: number;
  title: string;
  type: string;
  size: string;
  icon: string;
  iconColor: string;
  courseCode: string;
  url: string;
  fileName?: string;
}

interface CourseSection {
  courseCode: string;
  courseName: string;
  borderColor: string;
  materials: Material[];
}

@Component({
  selector: 'app-course-students',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './course-students.html',
  styleUrl: './course-students.css',
})
export class CourseStudents implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private doctorService = inject(DoctorService);
  private courseService = inject(CourseService);
  private materialService = inject(MaterialService);

  // Data
  currentDoctor: any;
  courses: Course[] = [];
  enrollments: CourseEnrollment[] = [];
  students: Student[] = [];

  sections: CourseSection[] = [];
  filteredSections: CourseSection[] = [];
  
  selectedType = 'All Types';
  searchQuery = '';

  // If a `course` query param arrives before courses finish loading we'll store it here
  pendingCourseCode: string | null = null;

  selectedCourse: number | '' = '';
  selectedCourseCode = 'All Courses';
  isLoading = false;

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();

    // Check for course parameter from URL
    this.route.queryParams.subscribe((params) => {
      if (params['course']) {
        const code = params['course'];

        // If courses not loaded yet, save the code and apply it after load
        if (!this.courses || this.courses.length === 0) {
          this.pendingCourseCode = code;
          return;
        }

        const course = this.courses.find((c) => c.courseCode === code);
        if (course) {
          this.selectedCourse = course.id;
          this.onCourseChange();
        }
      }
    });
  }

  loadCourses() {
    this.isLoading = true;
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log('Loaded courses:', this.courses);

          // If there was a pending course query param (arrived earlier), apply it now
          if (this.pendingCourseCode) {
            const course = this.courses.find((c) => c.courseCode === this.pendingCourseCode);
            if (course) {
              this.selectedCourse = course.id;
              this.onCourseChange();
            }
            this.pendingCourseCode = null;
          }

          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Could not load courses:', err);
        this.isLoading = false;
      },
    });
  }

  onCourseChange() {
    if (this.selectedCourse === '' || this.selectedCourse == null) {
      this.students = [];
      return;
    }

    this.isLoading = true;
    this.students = [];

    // Load enrollments for the selected course
    this.courseService.getCourseEnrollments(this.selectedCourse as number).subscribe({
      next: (enrollResponse) => {
        if (enrollResponse.success && enrollResponse.data) {
          const enrollments = Array.isArray(enrollResponse.data)
            ? enrollResponse.data
            : [enrollResponse.data];

          console.log('Enrollments:', enrollments);

          // Load student details for each enrollment
          let loadedCount = 0;
          const totalEnrollments = enrollments.length;

          if (totalEnrollments === 0) {
            this.isLoading = false;
            return;
          }

          enrollments.forEach((enrollment: CourseEnrollment) => {
            this.students = this.students.concat(enrollment.student ? [enrollment.student] : []);
            loadedCount++;
          });
          this.isLoading = false;
          console.log('Loaded students:', this.students);
        } else {
          this.isLoading = false;
        }
        this.loadMaterialsForCourse(this.selectedCourse as number);

      },
      error: (err) => {
        console.error('Error loading enrollments:', err);
        this.isLoading = false;
      },
    });
  }

  private getDefaultIcon(type: string): string {
    if (type?.includes('PDF')) return 'picture_as_pdf';
    if (type?.includes('VIDEO')) return 'play_circle';
    if (type?.includes('RECORDING')) return 'mic';
    if (type?.includes('TEXTBOOK')) return 'menu_book';
    return 'description';
  }

  private getDefaultColor(type: string): string {
    if (type?.includes('PDF')) return 'primary-icon';
    if (type?.includes('VIDEO')) return 'red-icon';
    if (type?.includes('RECORDING')) return 'amber-icon';
    return 'green-icon';
  }

  filterMaterials() {
    let filtered = [...this.sections];

    // Filter by material type
    if (this.selectedType && this.selectedType !== 'All Types') {
      filtered = filtered.map(section => ({
        ...section,
        materials: section.materials.filter(m => m.type === this.selectedType),
      }));
    }

    // Filter by search query
    if (this.searchQuery && this.searchQuery.trim()) {
      const query = this.searchQuery.toLowerCase().trim();
      filtered = filtered.map(section => ({
        ...section,
        materials: section.materials.filter(
          m =>
            m.title.toLowerCase().includes(query) ||
            m.type.toLowerCase().includes(query)
        ),
      }));
    }

    // Keep only sections with materials
    this.filteredSections = filtered.filter(s => s.materials.length > 0);
  }

  loadMaterialsForCourse(courseId: number) {
    this.sections = [];
  this.filteredSections = [];
    this.materialService.getMaterialsByCourse(courseId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const materialsData = Array.isArray(response.data)
            ? response.data
            : response.data
              ? [response.data]
              : [];

          const materials = materialsData.map((material: any) => ({
            id: material.materialId || material.id,
            title: material.title || material.materialTitle,
            type: material.type || 'PDF',
            size: material.formattedFileSize || material.fileSize || 'Unknown',
            icon: material.iconName || this.getDefaultIcon(material.type),
            iconColor: material.iconColor || this.getDefaultColor(material.type),
            courseCode: material.courseCode || 'Unknown',
            url: material.url || '',
            fileName: material.fileName || material.title || material.materialTitle,
          }));

          this.processMaterialsIntoSections(materials);
        }
        this.filterMaterials();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading course materials:', err);
        this.isLoading = false;
      }
    });
  }

  private processMaterialsIntoSections(materials: Material[]) {
    const colorMap = ['blue', 'green', 'pink', 'purple', 'amber', 'red'];
    const grouped = new Map<string, Material[]>();

    materials.forEach((material) => {
      const courseCode = material.courseCode || 'Unknown';

      if (!grouped.has(courseCode)) {
        grouped.set(courseCode, []);
      }

      const mat: Material = {
        id:  material.id,
        title:  material.title,
        type: material.type || 'PDF',
        // Use formatted size from backend, or fallback to raw size
        size: material.size || 'Unknown',
        // Use icon and color from backend
        icon: material.icon || this.getDefaultIcon(material.type),
        iconColor: material.iconColor || this.getDefaultColor(material.type),
        courseCode: courseCode,
        url: material.url || '',
        fileName: material.fileName,
      };

      grouped.get(courseCode)!.push(mat);
    });

    this.sections = Array.from(grouped.entries()).map((entry, index) => ({
      courseCode: entry[0],
      courseName: entry[0],
      borderColor: colorMap[index % colorMap.length] + '-border',
      materials: entry[1],
    }));
  }

  onSearchChange() {
    this.filterMaterials();
  }

  onFilterChange() {
    this.filterMaterials();
  }

  getCourseOptions(): string[] {
    const courses = ['All Courses'];
    this.sections.forEach(section => {
      if (!courses.includes(section.courseCode)) {
        courses.push(section.courseCode);
      }
    });
    return courses;
  }

  getMaterialTypes(): string[] {
    const types = ['All Types'];
    this.sections.forEach(section => {
      section.materials.forEach(material => {
        if (!types.includes(material.type)) {
          types.push(material.type);
        }
      });
    });
    return types;
  }

  downloadMaterial(material: Material): void {
    if (material.url) {
      this.materialService.downloadMaterial(material.url, material.fileName);
    } else {
      console.error('No URL available for material:', material.title);
    }
  }

  viewMaterial(material: Material): void {
    if (material.url) {
      this.materialService.viewMaterial(material.url, material.fileName);
    } else {
      console.error('No URL available for material:', material.title);
    }
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
