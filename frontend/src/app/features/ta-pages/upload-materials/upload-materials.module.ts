import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UploadMaterialsRoutingModule } from './upload-materials-routing-module';
import { UploadMaterials } from './upload-materials';

@NgModule({
  declarations: [],
  imports: [CommonModule, UploadMaterialsRoutingModule, UploadMaterials],
})
export class UploadMaterialsModule {}
