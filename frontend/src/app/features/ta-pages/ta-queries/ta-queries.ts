import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';

interface Query {
  id: number;
  studentName: string;
  studentEmail: string;
  course: string;
  subject: string;
  queryText: string;
  timestamp: Date;
  status: 'new' | 'in-progress' | 'resolved';
  responses?: string[];
}

@Component({
  selector: 'app-ta-queries',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './ta-queries.html',
  styleUrls: ['./ta-queries.css']
})
export class TaQueriesComponent implements OnInit {
  selectedTab: string = 'all';
  selectedQuery: Query | null = null;
  responseText: string = '';

  queries: Query[] = [
    {
      id: 1,
      studentName: 'Alice Johnson',
      studentEmail: 'alice@university.edu',
      course: 'CS101',
      subject: 'Question about recursion',
      queryText: 'I am having trouble understanding how recursion works. Can you explain with an example?',
      timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000),
      status: 'new'
    },
    {
      id: 2,
      studentName: 'Bob Smith',
      studentEmail: 'bob@university.edu',
      course: 'MATH201',
      subject: 'Integration by parts help',
      queryText: 'Can you help me with integration by parts formula?',
      timestamp: new Date(Date.now() - 5 * 60 * 60 * 1000),
      status: 'in-progress',
      responses: ['Try working through this example...']
    },
    {
      id: 3,
      studentName: 'Charlie Davis',
      studentEmail: 'charlie@university.edu',
      course: 'CS101',
      subject: 'Loop implementation',
      queryText: 'How do I implement a for loop in Python?',
      timestamp: new Date(Date.now() - 24 * 60 * 60 * 1000),
      status: 'resolved',
      responses: ['Here is a simple example...', 'Let me know if you have more questions']
    }
  ];

  filteredQueries: Query[] = [];

  ngOnInit() {
    this.filterQueries();
    if (this.queries.length > 0) {
      this.selectedQuery = this.queries[0];
    }
  }

  filterQueries() {
    this.filteredQueries = this.queries.filter(q =>
      this.selectedTab === 'all' || q.status === this.selectedTab
    );
  }

  selectTab(tab: string) {
    this.selectedTab = tab;
    this.filterQueries();
  }

  selectQuery(query: Query) {
    this.selectedQuery = query;
    this.responseText = '';
  }

  sendResponse() {
    if (!this.responseText.trim() || !this.selectedQuery) return;

    if (!this.selectedQuery.responses) {
      this.selectedQuery.responses = [];
    }
    this.selectedQuery.responses.push(this.responseText);
    this.selectedQuery.status = 'in-progress';
    this.responseText = '';
  }

  markResolved() {
    if (this.selectedQuery) {
      this.selectedQuery.status = 'resolved';
    }
  }

  getStatusColor(status: string): string {
    const colors: { [key: string]: string } = {
      'new': '#FFB74D',
      'in-progress': '#42A5F5',
      'resolved': '#4CAF50'
    };
    return colors[status] || '#BDBDBD';
  }
}
