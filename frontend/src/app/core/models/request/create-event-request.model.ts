export interface CreateEventRequest {
  eventTitle: string;
  eventDescription: string;
  eventDateTime: string;
  location: string;
  eventType: string;
  courseCode?: string;
}
