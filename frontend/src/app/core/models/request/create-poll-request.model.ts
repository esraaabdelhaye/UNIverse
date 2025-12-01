export interface CreatePollRequest {
  pollQuestion: string;
  options: string[];
  endDate: string;
  visibility: string;
}
