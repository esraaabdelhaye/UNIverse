import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewMaterialsRoutingModule } from './view-materials-routing-module';
import { ViewMaterials } from './view-materials';

@NgModule({
  declarations: [],
  imports: [CommonModule, ViewMaterialsRoutingModule, ViewMaterials],
})
export class ViewMaterialsModule {}
