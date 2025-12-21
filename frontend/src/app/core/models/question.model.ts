export interface Question {
  questionId?: string;
  courseCode: string;
  askedBy?: string;
  askerName?: string;
  questionTitle: string;
  questionContent: string;
  askedDate?: string; // ISO string
  answerCount?: number;
  viewCount?: number;
  status?: string; // e.g., PENDING, ANSWERED
  priority?: string;
  tags?: string[];
  upvotes?: number;
  answer?: string;
}

export interface AskQuestionRequest {
  courseCode: string;
  questionTitle: string;
  questionContent: string;
  tags?: string[];
  askedDate?: string;
  answerCount?: number;
  status?: string;
  priority?: string;
}