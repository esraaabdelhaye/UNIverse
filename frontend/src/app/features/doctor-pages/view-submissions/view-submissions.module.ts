import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewSubmissionsRoutingModule } from './view-submissions-routing.module';
import { ViewSubmissions } from './view-submissions';

@NgModule({
  declarations: [],
  imports: [CommonModule, ViewSubmissionsRoutingModule, ViewSubmissions],
})
export class ViewSubmissionsModule {}
