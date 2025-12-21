import {Component, OnInit, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {AuthService} from '../../../core/services/auth.service';
import {StudentService} from '../../../core/services/student.service';
import {QuestionService} from '../../../core/services/question.service';
import {AskQuestionRequest, Question} from '../../../core/models/question.model';

@Component({
  selector: 'app-student-questions',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './questions.html',
  styleUrl: './questions.css',
})
export class Questions implements OnInit {
  private auth = inject(AuthService);
  private studentService = inject(StudentService);
  questionService = inject(QuestionService);
  private router = inject(Router);
  private authService = inject(AuthService);

  // popup for viewing answers
  showAnswerModal = false;
  selectedAnswer: Question | null = null;
  openAnswerPopUp(q: Question) {
    this.selectedAnswer = q;
    this.showAnswerModal = true;
  }

  closeAnswerPopUp() {
    this.showAnswerModal = false;
    this.selectedAnswer = null;
  }

  currentUser: any;
  courses: { id: number; courseCode: string }[] = [];

  // Form
  form: AskQuestionRequest = {
    courseCode: '',
    questionTitle: '',
    questionContent: '',
    tags: [],
  };
  tagInput = '';
  submitting = false;

  // Lists
  myPending: Question[] = [];
  myAnswered: Question[] = [];
  filterText = '';
  activeTab: 'ask' | 'pending' | 'answered' = 'ask';

  // Edit state
  editingId: string | null = null;
  editModel: Partial<Question> = {};
  savingEdit = false;

  isLoading = true;
  statusMessage = '';
  statusType: 'success' | 'error' | '' = '';

  ngOnInit(): void {
    this.currentUser = this.auth.getCurrentUser();
    if (!this.currentUser) {
      return;
    }
    this.loadData();
  }

  loadData(): void {
    const studentId = this.currentUser.studentId || this.currentUser.id;

    // Load student's courses then questions
    this.studentService.getStudentCourses(parseInt(studentId)).subscribe({
      next: (res) => {
        const data = Array.isArray(res.data) ? res.data : res.data ? [res.data] : [];
        this.courses = data.map((c: any) => ({id: c.id, courseCode: c.courseCode}));
        if (!this.form.courseCode && this.courses.length) {
          this.form.courseCode = this.courses[0].courseCode;
        }
      },
      error: () => {
      },
    });

    this.questionService.getByStudent(studentId, 0, 100).subscribe({
      next: (res) => {
        const list = Array.isArray(res.data) ? res.data : res.data ? [res.data] : [];
        this.myPending = list.filter((q) => (q.status || 'PENDING') !== 'ANSWERED');
        this.myAnswered = list.filter((q) => q.status === 'ANSWERED');
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  submitQuestion(): void {
    if (!this.form.courseCode) this.toast("Please select a course.","error");
    if (!this.form.questionTitle.trim()) this.toast("Please enter a question title.","error");
    if (!this.form.questionContent.trim()) this.toast("Please enter the question content.","error");

    this.submitting = true;
    let questionRequest: AskQuestionRequest = {
      courseCode: this.form.courseCode,
      questionTitle: this.form.questionTitle,
      questionContent: this.form.questionContent,
      tags: this.form.tags,
    }

    this.questionService.askQuestion(questionRequest).subscribe(response => {
      if (response.success) {
        this.toast('Question submitted successfully!', 'success');
        this.resetForm();
        this.loadData();
        this.submitting = false;
      }
      else {
        this.toast('Failed to submit question. Please try again.', 'error');
        this.resetForm();
        this.submitting = false;
      }

    })
  }

  startEdit(q: Question): void {
    // Begin editing a question: store the editing id and a shallow copy of the model
    this.editingId = q?.questionId != null ? String(q.questionId) : null;
    this.editModel = { ...q };
  }

  cancelEdit(): void {
    // Cancel current edit
    this.editingId = null;
    this.editModel = {};
    this.savingEdit = false;
  }

  saveEdit(q: Question): void {
    if (this.editingId == null) {
      this.toast('No edit in progress.', 'error');
      return;
    }
    if (q?.questionId == null) {
      this.toast('Cannot save: missing question ID.', 'error');
      return;
    }

    this.savingEdit = true;

      this.questionService.updateQuestion(q.questionId, this.editModel).subscribe({
        next: (res: any) => {
          this.toast('Question updated.', 'success');
          this.cancelEdit();
          this.loadData();
        },
        error: () => {
          this.toast('Failed to update question.', 'error');
        },
        complete: () => {
          this.savingEdit = false;
        }
      });
  }

  delete(q: Question): void {
    if (q.questionId == null) {
      this.toast('Cannot delete question: Missing question ID.', 'error');
      return;
    }

    this.questionService.deleteQuestion(q.questionId).subscribe({
      next: () => {
        this.toast('Question deleted.', 'success');
        this.myPending = this.myPending.filter(item => item.questionId !== q.questionId);
        this.myAnswered = this.myAnswered.filter(item => item.questionId !== q.questionId);
      },
      error: () => {
        this.toast('Failed to delete question.', 'error');
      }
    });
  }

  removeTag(i: number): void {
    // Remove tag by index if valid
    if (Array.isArray(this.form.tags) && i >= 0 && i < this.form.tags.length) {
      this.form.tags.splice(i, 1);
    }
  }

  addTagFromInput(): void {
    const tag = this.tagInput?.trim();
    if (!tag) return;
    if (!this.form.tags) this.form.tags = [];
    // prevent duplicates
    if (!this.form.tags.includes(tag)) {
      this.form.tags.push(tag);
    }
    this.tagInput = '';
  }

  filtered(list: Question[]): Question[] {
    const text = this.filterText?.trim().toLowerCase() || '';
    let base: Question[] = list || [];

    // Respect active tab if set
    if (this.activeTab === 'pending') base = this.myPending;
    else if (this.activeTab === 'answered') base = this.myAnswered;
    else if (this.activeTab === 'ask') return [];

    if (!text) return [...base];

    return base.filter(q => {
      const title = (q.questionTitle || '').toLowerCase();
      const content = (q.questionContent || '').toLowerCase();
      const tags = Array.isArray(q.tags) ? q.tags.join(' ').toLowerCase() : '';
      return title.includes(text) || content.includes(text) || tags.includes(text);
    });
  }

  resetForm(): void {
    this.form.tags = [] ;
    this.tagInput = '';
    this.form.questionTitle = '';
    this.form.questionContent = '';
    this.form.courseCode= this.courses.length ? this.courses[0].courseCode : '';
  }

  toast(msg: string, type: 'success' | 'error') {
    this.statusMessage = msg;
    this.statusType = type;
    setTimeout(() => {
      this.statusMessage = '';
      this.statusType = '';
    }, 4000);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
