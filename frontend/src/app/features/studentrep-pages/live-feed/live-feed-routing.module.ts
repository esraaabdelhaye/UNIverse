import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LiveFeed } from './live-feed';

const routes: Routes = [
  {
    path: '',
    component: LiveFeed,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LiveFeedRoutingModule {}
