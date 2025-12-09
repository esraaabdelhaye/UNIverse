import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';

interface Material {
  id: number;
  title: string;
  type: 'lecture' | 'reading' | 'problem' | 'syllabus';
  fileName: string;
  course: string;
  uploadDate: Date;
  size: string;
}

@Component({
  selector: 'app-professor-materials',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './professor-materials.html',
  styleUrls: ['./professor-materials.css']
})
export class ProfessorMaterialsComponent implements OnInit {
  selectedCourse: string = 'CS101';
  selectedType: string = 'lecture';
  materialTitle: string = '';
  materialDescription: string = '';
  selectedFile: File | null = null;

  courses = ['CS101', 'CS201', 'CS301', 'CS401'];
  materialTypes = [
    { value: 'lecture', label: 'Lecture Slides' },
    { value: 'reading', label: 'Reading Material' },
    { value: 'problem', label: 'Problem Sets' },
    { value: 'syllabus', label: 'Syllabus' }
  ];

  recentMaterials: Material[] = [
    { id: 1, title: 'Week 5 - React Hooks', type: 'lecture', fileName: 'Week5_ReactHooks.pptx', course: 'CS101', uploadDate: new Date(2024, 9, 15), size: '5.1 MB' },
    { id: 2, title: 'Reading: Chapter 1 & 2', type: 'reading', fileName: 'Chapter1_2.pdf', course: 'CS101', uploadDate: new Date(2024, 9, 14), size: '2.3 MB' },
    { id: 3, title: 'Problem Set 1', type: 'problem', fileName: 'ProblemSet1.pdf', course: 'CS101', uploadDate: new Date(2024, 9, 13), size: '450 KB' }
  ];

  ngOnInit() {
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadMaterial() {
    if (!this.materialTitle || !this.selectedFile || !this.selectedCourse) {
      alert('Please fill all required fields');
      return;
    }

    const newMaterial: Material = {
      id: this.recentMaterials.length + 1,
      title: this.materialTitle,
      type: this.selectedType as any,
      fileName: this.selectedFile.name,
      course: this.selectedCourse,
      uploadDate: new Date(),
      size: (this.selectedFile.size / 1024 / 1024).toFixed(2) + ' MB'
    };

    this.recentMaterials.unshift(newMaterial);

    // Reset form
    this.materialTitle = '';
    this.materialDescription = '';
    this.selectedFile = null;

    alert('Material uploaded successfully!');
  }

  getMaterialIcon(type: string): string {
    const icons: { [key: string]: string } = {
      'lecture': 'presentation',
      'reading': 'library_books',
      'problem': 'assignment',
      'syllabus': 'menu_book'
    };
    return icons[type] || 'description';
  }

  deleteMaterial(material: Material) {
    if (confirm('Are you sure you want to delete this material?')) {
      this.recentMaterials = this.recentMaterials.filter(m => m.id !== material.id);
    }
  }
}
