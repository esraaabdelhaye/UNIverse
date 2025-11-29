import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageStudentGrps } from './manage-student-grps';

const routes: Routes = [
  {
    path: '',
    component: ManageStudentGrps,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageStudentGrpsRoutingModule {}
