import {QueryResponse} from './QueryResponse';

export interface StudentQuery {
  id: string;
  studentId: string;
  studentName: string;
  studentEmail: string;
  courseId: string;
  subject: string;
  queryText: string;
  timestamp: Date;
  status: 'new' | 'in-progress' | 'resolved';
  responses?: QueryResponse[];
}
