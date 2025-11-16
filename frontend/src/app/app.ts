import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DashBoard } from "./features/rep-dash-board/Views/dash-board/dash-board";
import { RepDashBoard } from './features/rep-dash-board/rep-dash-board';
import { Sidebar } from './features/rep-dash-board/Components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DashBoard, RepDashBoard, Sidebar],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
