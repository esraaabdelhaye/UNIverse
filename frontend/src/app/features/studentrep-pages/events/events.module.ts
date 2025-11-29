import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventsRoutingModule } from './events-routing.module';
import { Events } from './events';

@NgModule({
  declarations: [],
  imports: [CommonModule, EventsRoutingModule, Events],
})
export class EventsModule {}
