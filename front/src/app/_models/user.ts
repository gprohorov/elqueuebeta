import { Doctor } from './index';

export class User {
  id: string;
  userame: string;
  enabled: boolean;
  authorities: string[];
  info: Doctor;
}
