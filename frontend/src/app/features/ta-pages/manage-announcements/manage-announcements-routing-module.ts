import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ManageAnnouncements } from './manage-announcements';

const routes: Routes = [
  {
    path: '',
    component: ManageAnnouncements,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageAnnouncementsRoutingModule {}
