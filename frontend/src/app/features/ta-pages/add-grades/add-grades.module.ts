import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddGradesRoutingModule } from './add-grades-routing-module';
import { AddGrades } from './add-grades';

@NgModule({
  declarations: [],
  imports: [CommonModule, AddGradesRoutingModule, AddGrades],
})
export class AddGradesModule {}
