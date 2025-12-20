import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { MaterialService } from '../../../core/services/material.service';
import { StudentService } from '../../../core/services/student.service';

interface Material {
  id: number;
  title: string;
  type: string;
  size: string;
  icon: string;
  iconColor: string;
  courseCode: string;
  url: string;
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
  private materialService = inject(MaterialService);
  private studentService = inject(StudentService);

  // User data
  currentUser: any;

  // Material data
  sections: CourseSection[] = [];
  filteredSections: CourseSection[] = [];

  // Filters
  searchQuery = '';
  selectedCourse = 'All Courses';
  selectedType = 'All Types';

  // Loading state
  isLoading = true;

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    // Check if course ID was passed as query param
    this.route.queryParams.subscribe(params => {
      if (params['courseId']) {
        this.loadMaterialsForCourse(parseInt(params['courseId']));
      } else {
        this.loadMaterials();
      }
    });
  }

  loadMaterials() {
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    // First load student's courses
    this.studentService.getStudentCourses(studentId).subscribe({
      next: (courseResponse) => {
        if (courseResponse.success && courseResponse.data) {
          const coursesArray = Array.isArray(courseResponse.data)
            ? courseResponse.data
            : courseResponse.data
              ? [courseResponse.data]
              : [];

          if (coursesArray.length === 0) {
            this.isLoading = false;
            return;
          }

          // Load materials for each course
          const allMaterials: Material[] = [];
          let loadedCount = 0;

          coursesArray.forEach((course: any) => {
            const courseId = course.id;

            this.materialService.getMaterialsByCourse(courseId).subscribe({
              next: (response) => {
                if (response.success && response.data) {
                  const materialsData = Array.isArray(response.data)
                    ? response.data
                    : response.data
                      ? [response.data]
                      : [];

                  materialsData.forEach((material: any) => {
                    allMaterials.push({
                      id: material.materialId || material.id,
                      title: material.title || material.materialTitle,
                      type: material.type || 'PDF',
                      size: material.formattedFileSize || material.fileSize || 'Unknown',
                      icon: material.iconName || this.getDefaultIcon(material.type),
                      iconColor: material.iconColor || this.getDefaultColor(material.type),
                      courseCode: material.courseCode || course.courseCode,
                      url: material.url || '',
                    });
                  });
                }

                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  this.processMaterialsIntoSections(allMaterials);
                  this.filterMaterials();
                  this.isLoading = false;
                }
              },
              error: (err) => {
                console.error(`Error loading materials for course ${courseId}:`, err);
                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  this.processMaterialsIntoSections(allMaterials);
                  this.filterMaterials();
                  this.isLoading = false;
                }
              }
            });
          });
        } else {
          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error loading student courses:', err);
        this.isLoading = false;
      }
    });
  }

  loadMaterialsForCourse(courseId: number) {
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

  /**
   * Process materials and group by course
   */
  private processMaterialsIntoSections(materials: Material[]) {
    const colorMap = ['blue', 'green', 'pink', 'purple', 'amber', 'red'];
    const grouped = new Map<string, Material[]>();

    materials.forEach((material) => {
      const courseCode = material.courseCode || 'Unknown';

      if (!grouped.has(courseCode)) {
        grouped.set(courseCode, []);
      }

      grouped.get(courseCode)!.push(material);
    });

    this.sections = Array.from(grouped.entries()).map((entry, index) => ({
      courseCode: entry[0],
      courseName: entry[0],
      borderColor: colorMap[index % colorMap.length] + '-border',
      materials: entry[1],
    }));
  }

  /**
   * Fallback icon mapping (used only if backend doesn't provide icon)
   */
  private getDefaultIcon(type: string): string {
    if (type?.includes('PDF')) return 'picture_as_pdf';
    if (type?.includes('VIDEO')) return 'play_circle';
    if (type?.includes('RECORDING')) return 'mic';
    if (type?.includes('TEXTBOOK')) return 'menu_book';
    return 'description';
  }

  /**
   * Fallback color mapping (used only if backend doesn't provide color)
   */
  private getDefaultColor(type: string): string {
    if (type?.includes('PDF')) return 'primary-icon';
    if (type?.includes('VIDEO')) return 'red-icon';
    if (type?.includes('RECORDING')) return 'amber-icon';
    return 'green-icon';
  }

  filterMaterials() {
    let filtered = [...this.sections];

    // Filter by course
    if (this.selectedCourse && this.selectedCourse !== 'All Courses') {
      filtered = filtered.filter(s => s.courseCode === this.selectedCourse);
    }

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
      this.materialService.downloadMaterial(material.url);
    } else {
      console.error('No URL available for material:', material.title);
    }
  }

  viewMaterial(material: Material): void {
    if (material.url) {
      this.materialService.viewMaterial(material.url);
    } else {
      console.error('No URL available for material:', material.title);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
