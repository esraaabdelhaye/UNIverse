import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenerateTimetableRoutingModule } from './generate-timetable-routing-module';
import { GenerateTimetable } from './generate-timetable';

@NgModule({
  declarations: [],
  imports: [CommonModule, GenerateTimetableRoutingModule, GenerateTimetable],
})
export class GenerateTimetableModule {}
