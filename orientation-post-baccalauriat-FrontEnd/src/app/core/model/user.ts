import {Role} from "../enums/role";

export interface User {
  userId: number;
  userName: string;
  email: string;
  password: string;
  role: Role;
}
