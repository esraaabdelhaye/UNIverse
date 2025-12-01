export interface Event {
  eventId: string;
  eventTitle: string;
  eventDescription: string;
  eventDateTime: string;
  location: string;
  eventType: 'CLASS' | 'STUDY_SESSION' | 'UNIVERSITY_EVENT';
  attendeeCount: number;
}
