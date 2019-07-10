import { Doctor } from './index';

export class User {
  id: string;
  username: string;
  password: string;
  enabled: boolean;
  authorities: string[];
  info: Doctor;
}
