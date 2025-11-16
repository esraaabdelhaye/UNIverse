import { Component } from '@angular/core';
import { Sidebar } from './Components/sidebar/sidebar';
import { RouterOutlet } from '@angular/router';
import { Header } from './Components/header/header';


@Component({
  selector: 'app-rep-dash-board',
  standalone: true,
  imports: [Sidebar, RouterOutlet, Header],
  templateUrl: './rep-dash-board.html',
  styleUrls: ['./rep-dash-board.css'],
})
export class RepDashBoard {

}
