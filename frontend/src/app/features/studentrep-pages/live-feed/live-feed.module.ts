import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LiveFeedRoutingModule } from './live-feed-routing.module';
import { LiveFeed } from './live-feed';

@NgModule({
  declarations: [],
  imports: [CommonModule, LiveFeedRoutingModule, LiveFeed],
})
export class LiveFeedModule {}
