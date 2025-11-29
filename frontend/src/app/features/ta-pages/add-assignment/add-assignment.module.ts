import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAssignmentRoutingModule } from './add-assignment-routing-module';
import { AddAssignment } from './add-assignment';

@NgModule({
  declarations: [],
  imports: [CommonModule, AddAssignmentRoutingModule, AddAssignment],
})
export class AddAssignmentModule {}
