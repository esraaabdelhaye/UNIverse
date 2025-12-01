export interface StudentGroup {
  groupId: string;
  groupName: string;
  groupDescription: string;
  memberCount: number;
  activityLevel: 'ACTIVE' | 'INACTIVE' | 'HOT';
  createdDate: string;
  groupImage?: string;
}
