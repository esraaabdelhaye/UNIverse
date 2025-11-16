import { Component } from '@angular/core';
import { CommunityFeed } from '../../Components/community-feed/community-feed';
import { StudentsGroups } from '../../Components/students-groups/students-groups';
import { Polls } from '../../Components/polls/polls';

@Component({
  selector: 'app-dash-board',
  standalone: true,
  imports: [CommunityFeed, StudentsGroups, Polls],
  templateUrl: './dash-board.html',
  styleUrls: ['./dash-board.css'],
})
export class DashBoard {
    
}
