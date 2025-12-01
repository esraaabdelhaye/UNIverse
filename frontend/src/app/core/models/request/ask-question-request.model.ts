export interface AskQuestionRequest {
  courseCode: string;
  questionTitle: string;
  questionContent: string;
  tags?: string[];
}
