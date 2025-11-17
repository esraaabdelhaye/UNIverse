export interface Course {
  id: string;
  code: string;
  name: string;
  description?: string;
  credits: number;
  isActive: boolean;
  availableToLevels: number[];
  department?: string;
}

