import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageFacultyRoutingModule } from './manage-faculty-routing-module';
import { ManageFaculty } from './manage-faculty';

@NgModule({
  declarations: [],
  imports: [CommonModule, ManageFacultyRoutingModule, ManageFaculty],
})
export class ManageFacultyModule {}
