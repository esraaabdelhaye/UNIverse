import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadMaterial } from './upload-material';

const routes: Routes = [
  {
    path: '',
    component: UploadMaterial,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UploadMaterialRoutingModule {}
