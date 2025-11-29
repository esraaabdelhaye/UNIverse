import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UploadMaterials } from './upload-materials';

const routes: Routes = [
  {
    path: '',
    component: UploadMaterials,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UploadMaterialsRoutingModule {}
