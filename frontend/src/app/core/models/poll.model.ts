import {PollOption} from './poll-option.model';

export interface Poll {
  pollId: string;
  pollQuestion: string;
  options: PollOption[];
  status: 'ACTIVE' | 'CLOSED';
  totalVotes: number;
  endDate: string;
}
