import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewGradesRoutingModule } from './view-grades-routing-module';
import { ViewGrades } from './view-grades';

@NgModule({
  declarations: [],
  imports: [CommonModule, ViewGradesRoutingModule, ViewGrades],
})
export class ViewGradesModule {}
