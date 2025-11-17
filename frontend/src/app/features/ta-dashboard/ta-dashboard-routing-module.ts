import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { TaDashboard } from './ta-dashboard';

const routes: Routes = [
  {
    path: '',
    component: TaDashboard,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TaDashboardRoutingModule {}
