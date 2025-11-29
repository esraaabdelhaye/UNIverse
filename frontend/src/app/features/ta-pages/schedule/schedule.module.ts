import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScheduleRoutingModule } from './schedule-routing-module';
import { Schedule } from './schedule';

@NgModule({
  declarations: [],
  imports: [CommonModule, ScheduleRoutingModule, Schedule],
})
export class ScheduleModule {}
