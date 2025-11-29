import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Events } from './events';

const routes: Routes = [
  {
    path: '',
    component: Events,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EventsRoutingModule {}
