import {User} from "./user";
import {BacType} from "../enums/bac-type";
import {Institution} from "./institution";
import {Review} from "./review";


export interface Student extends User {
  dateOfBirth: Date;
  bacScore: number;
  location: string;
  bacType: BacType;
  institutions: Institution[];
  reviews: Review[];
}

