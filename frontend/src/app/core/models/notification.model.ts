export interface Notification {
  notificationId: string;
  userId: string;
  title: string;
  message: string;
  notificationType: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  isRead: boolean;
  createdDate: string;
  readDate?: string;
  relatedEntityId?: string;
  relatedEntityType?: string;
}
