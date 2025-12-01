export interface Question {
  questionId: string;
  courseCode: string;
  askedBy: string;
  askerName: string;
  questionTitle: string;
  questionContent: string;
  askedDate: string;
  answerCount: number;
  viewCount: number;
  status: 'NEW' | 'IN_PROGRESS' | 'RESOLVED';
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  tags?: string[];
  upvotes: number;
}
