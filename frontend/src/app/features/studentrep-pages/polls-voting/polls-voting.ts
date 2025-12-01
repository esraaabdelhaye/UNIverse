import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-polls-voting',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './polls-voting.html',
  styleUrl: './polls-voting.css',
})
export class PollsVoting {

}
