import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageAnnouncementsRoutingModule } from './manage-announcements-routing-module';
import { ManageAnnouncements } from './manage-announcements';

@NgModule({
  declarations: [],
  imports: [CommonModule, ManageAnnouncementsRoutingModule, ManageAnnouncements],
})
export class ManageAnnouncementsModule {}
