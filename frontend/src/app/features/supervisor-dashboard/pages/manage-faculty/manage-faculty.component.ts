import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { FacultyMember } from '../../../../core/models/faculty.model';
import { SupervisorService } from '../../../../core/services/supervisor.service';

@Component({
  selector: 'app-manage-faculty',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manage-faculty.component.html',
  styleUrls: ['./manage-faculty.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ManageFacultyComponent implements OnInit {
  
  public faculty$!: Observable<FacultyMember[]>;

  constructor(
    private supervisorService: SupervisorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.faculty$ = this.supervisorService.getFaculty();
  }

  addNewMember() {
    const newMember: FacultyMember = {
      id: 'f' + (Date.now()),
      name: 'New Member',
      email: 'newmember@university.edu',
      department: 'Computer Science',
      position: 'Teaching Assistant',
      imageUrl: 'https://placehold.co/100x100/E3F2FD/4A90E2?text=NM',
      status: 'Active'
    };
    
    this.supervisorService.addFacultyMember(newMember);
    this.cdr.markForCheck();
    
    // Prompt for editing
    setTimeout(() => {
      this.editMember(newMember);
    }, 100);
  }

  editMember(member: FacultyMember) {
    const name = prompt('Enter name:', member.name);
    if (name && name.trim()) {
      const updatedMember: FacultyMember = {
        ...member,
        name: name.trim()
      };
      this.supervisorService.updateFacultyMember(updatedMember);
      this.cdr.markForCheck();
    }
  }

  removeMember(member: FacultyMember) {
    if (confirm(`Are you sure you want to remove ${member.name}?`)) {
      this.supervisorService.removeFacultyMember(member.id);
      this.cdr.markForCheck();
    }
  }
}