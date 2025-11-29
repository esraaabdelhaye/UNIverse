import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ViewMaterials } from './view-materials';

const routes: Routes = [
  {
    path: '',
    component: ViewMaterials,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewMaterialsRoutingModule {}
