export interface GenerateTimetableRequest {
  semester: string;
  coursesToInclude: string[];
  instructorAvailability?: string[];
  roomCapacityPreferences?: string[];
  avoidBackToBackClasses: boolean;
  prioritizeMorningSlots: boolean;
}
