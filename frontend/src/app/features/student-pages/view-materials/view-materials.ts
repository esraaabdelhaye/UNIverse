import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
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
    this.materialService.getAllMaterials().subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.processMaterials(response.data);
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading materials:', err);
        this.isLoading = false;
      }
    });
  }

  loadMaterialsForCourse(courseId: number) {
    this.materialService.getMaterialsByCourse(courseId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.processMaterials(response.data, courseId);
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading course materials:', err);
        this.isLoading = false;
      }
    });
  }

  /**
   * Process materials from backend and group by course
   * All icon and size data now comes from the backend
   */
  private processMaterials(materials: any[], courseId?: number) {
    const colorMap = ['blue', 'green', 'pink', 'purple', 'amber', 'red'];
    const grouped = new Map<string, Material[]>();

    materials.forEach((material) => {
      const courseCode = material.courseCode || 'Unknown';

      if (!grouped.has(courseCode)) {
        grouped.set(courseCode, []);
      }

      const mat: Material = {
        id: material.materialId || material.id,
        title: material.materialTitle || material.title,
        type: material.materialType || 'PDF',
        // Use formatted size from backend, or fallback to raw size
        size: material.formattedFileSize || material.fileSize || 'Unknown',
        // Use icon and color from backend
        icon: material.iconName || this.getDefaultIcon(material.materialType),
        iconColor: material.iconColor || this.getDefaultColor(material.materialType),
        courseCode: courseCode,
        url: material.downloadUrl || material.url || '',
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

    this.filterMaterials();
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
