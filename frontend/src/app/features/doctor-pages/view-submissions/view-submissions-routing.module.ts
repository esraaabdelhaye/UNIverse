import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewSubmissions } from './view-submissions';

const routes: Routes = [
  {
    path: '',
    component: ViewSubmissions,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewSubmissionsRoutingModule {}
